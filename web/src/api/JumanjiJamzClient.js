import axios from "axios";
import BindingClass from "../util/bindingClass";
import Authenticator from "./authenticator";
import Header from "../components/header";

export default class JumanjiJamzClient extends BindingClass {
    constructor(props = {}) {
        super();
        const methodsToBind = ['clientLoaded', 'getIdentity', 'login', 'logout', 'handleError',
            'getChart', 'getSetList', 'createChart', 'createSetList', 'getAllCharts', 'updateSetList', 'updateChart'];
        this.bindClassMethods(methodsToBind, this);

        this.authenticator = new Authenticator();
        this.props = props;

        axios.defaults.baseURL = process.env.API_BASE_URL;
        this.axiosClient = axios;
        this.clientLoaded();
    }

    clientLoaded() {
        if(this.props.hasOwnProperty("onReady")) {
            this.props.onReady(this);
        }
    }

    /**
         * Get the identity of the current user
         * @param errorCallback (Optional) A function to execute if the call fails.
         * @returns The user information for the current user.
         */
    async getIdentity(errorCallback) {
        try {
            const isLoggedIn = await this.authenticator.isUserLoggedIn();

            if (!isLoggedIn) {
                return undefined;
            }

                return await this.authenticator.getCurrentUserInfo();
            } catch (error) {
                this.handleError(error, errorCallback)
            }
    }


    async login() {
            this.authenticator.login();
        }

    async logout() {
        this.authenticator.logout();
    }

    async getTokenOrThrow(unauthenticatedErrorMessage) {
        const isLoggedIn = await this.authenticator.isUserLoggedIn();
        if (!isLoggedIn) {
            throw new Error(unauthenticatedErrorMessage);
        }
            return await this.authenticator.getUserToken();
        }

    /**
    * Helper method to log the error and run any error functions.
    * @param error The error received from the server.
    * @param errorCallback (Optional) A function to execute if the call fails.
    */
    handleError(error, errorCallback) {
        console.error(error);
        const errorFromApi = error?.response?.data?.error_message;
        if (errorFromApi) {
            console.error(errorFromApi)
            error.message = errorFromApi;
        }
        if (errorCallback) {
            errorCallback(error);
        }
    }

    // Method for getting single chart with id param
    async getChart(id, errorCallBack) {
        try {
            const response = await this.axiosClient.get(`charts/${id}`);
            return response.data;
        } catch (error) {
            this.handleError(error, errorCallback);
        }
    }

    // Method for getting single setlist with id Param
    async getSetList(id, errorCallback) {
        try {
            const response = await this.axiosClient.get(`setlists/${id}`);
            return response.data;
        } catch (error) {
            this.handleError(error, errorCallback);
        }
    }

    // Method to call createChart APi
    async createChart(chartDetails, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Must be logged in to cerate chart");

            const payload = {
                name: chartDetails.name,
                artist: chartDetails.artist,
                content: chartDetails.content,
                bpm: chartDetails.bpm,
                genres: chartDetails.genres,
            };

            const response = await this.axiosClient.post(`charts/`, payload, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            console.log("response {}", response);
            return response.data;
        } catch (error) {
            this.handleError(error, errorCallback);
        }
    }

    // Method to call createSetList API
    async createSetList(setListDetails, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Must be logged in to create setlis");
            console.log("token {}", token);
            const payload = {
                name: setListDetails.name,
                charts: setListDetails.charts,
                genres: setListDetails.genres
            };
           

            const response = await this.axiosClient.post(`setlists`, payload, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });

            return response.data;
        } catch(error) {
          
            this.handleError(error, errorCallback);
        }
    }

    // Method for accessing the getAllCharts API
    async getAllCharts(id, errorCallback) {
        try {
            const response = await this.axiosClient.get (`all/charts/${id}`);
            return response.data;
        } catch (error) {
            this.handleError(error, errorCallback);
        }
    }

    // Method for accessing the updateChart API
    async updateChart(id, chartDetails, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only owners can modify chart");
            const payload = {
                name: chartDetails.name,
                artist: chartDetails.artist,
                bpm: chartDetails.bpm,
                content: chartDetails.content,
                genres: chartDetails.genres
            };

            const response = await this.put(`charts/${id}`, payload, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response
        } catch (error) {
            this.handleError(error, errorCallback);
        }
    }

    // Method for accessing the updateSetList API
    async updateSetList(id, setListDetails, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can update a setList");
            const payload = {
                name: setListDetails.name,
                charts: setListDetails.charts,
                genres: setListDetails.genres
            };

            const response = await this.put(`setlists/${id}`, payload, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data;
        } catch (error) {
            this.handleError(error, errorCallback);
        }
    }
}

