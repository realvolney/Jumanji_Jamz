import axios from "axios";
import BindingClass from "../util/bindingClass";
import Authenticator from "./authenticator";

export default class JumanjiJamzClient extends BindingClass {
    constructor(props = {}) {
        super();
        const methodsToBind = ['clientLoaded', 'getIdentity', 'login', 'logout', 'getTokenOrThrow'];
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
            const response = await.this.axiosClient.get(`setlists/${id}`);
            return response.data;
        } catch (error) {
            this.handleError(error, errorCallback);
        }
    }

    // Method to call createChart APi 
    async createChart(chartDetails, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can create charts");

            const payload = {
                name: chartDetails.name,
                artist: chartDetails.artist,
                content: chartDetails.content,
                bpm: chartDetails.bpm
            };

            const response = await this.axiosClient.post(`charts`, payload, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });

            return response.data;
        } catch (error) {
            this.handleError(error, errorCallback);
        }
    }
