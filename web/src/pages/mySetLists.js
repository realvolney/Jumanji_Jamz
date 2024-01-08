import DataStore from '../util/DataStore';
import BindingClass from "../util/bindingClass";
import JumanjiJamzClient from "../api/JumanjiJamzClient";
import Header from '../components/header';
import Authenticator from '../api/authenticator';

class MySetLists extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['showLoading', 'hideLoading', 'clientLoaded', 'mount',
         'displaySetlists', 'getHTMLForSetListResults', 'handleFormSubmission','displayLogInMessage'], this);
        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);
        this.client = new JumanjiJamzClient();
        this.authenticator = new Authenticator();
        console.log("MySetLists constructor");
    }

    

    showLoading() {
        document.getElementById('loading').innerText = "Loading Setlists";
    }

    hideLoading() {
        document.getElementById('loading').style.display = 'none';
    }

    async clientLoaded() {
        this.showLoading();
        
        try {
            const setLists = await this.client.mySetLists();
    
            const info = await this.authenticator.getCurrentUserInfo();
            console.log("info {}", info);
            const user = document.getElementById('setList-owner');
            user.innerText = info.name + "'s Setlists";
            this.hideLoading();
    
            console.log("SetLists {}", setLists);
    
            this.dataStore.set('setLists', setLists);
            this.displaySetlists();
        } catch (error) {
            this.displayLogInMessage();
            this.hideLoading();
        }
    }

    /**
     * Add the header to the page and load the VendorEventClient.
     */
    mount() {
        this.header.addHeaderToPage();
        this.clientLoaded();
        
        document.getElementById('add-chart').addEventListener('click', this.next);
        
    }
    

    displaySetlists() {
        const setLists = this.dataStore.get('setLists');
         const setListsResultsContainer = document.getElementById('search-results-container');
        // const searchCriteriaDisplay = document.getElementById('search-criteria-display');
        const setListsResultsDisplay = document.getElementById('search-results-display');

    
            setListsResultsContainer.classList.remove('hidden');
       
            setListsResultsDisplay.innerHTML = this.getHTMLForSetListResults(setLists);    
            this.handleFormSubmission();
    }

    displayLogInMessage() {
        const setLists = this.dataStore.get('setLists');
        const setListsResultsContainer = document.getElementById('search-results-container');
       
        const setListsResultsDisplay = document.getElementById('search-results-display');
        setListsResultsContainer.classList.remove('hidden');
        setListsResultsDisplay.innerHTML = '<h4>You must be logged in</h4>';
        
    }

    getHTMLForSetListResults(setLists) {
        if (!setLists) {
            return '<h4>You have no setlists</h4>';
        }

        let html = `<form>`
        html += '<table><tr><th>Name</th><th>Genres</th><th>Check to Delete Setlists</th></tr>';
        for (const res of setLists) {
            html += `
            <tr>
                <td>
                    <a href="setlist.html?id=${res.id}">${res.name}</a>
                </td>
        
                <td>${res.genres ? res.genres?.join(', ') : 'none'}</td>
                <td><input type="checkbox" value="${res.id}"></td>
            </tr>`;
        }
        html += '</table>';
        html += '<th><button class="add-chart-button" type="submit">Click to Delete Setlists</button></th>';
        html += `</form>`;

        return html;
    }
    handleFormSubmission() {
        const parentElement = document.getElementById('search-results-container');
        const setLists = this.dataStore.get('setLists');
        
        parentElement.addEventListener('submit', async (event) => {
            event.preventDefault();
            const searchCriteriaDisplay = document.getElementById('search-criteria-display');
            // const button = document.getElementById('add-chart-button');
            // button.innerText = "Loading..."
            searchCriteriaDisplay.innerText = "Loading..."
            if (event.target.tagName === 'FORM') {
                const form = event.target;
                const selectedSetlists = form.querySelectorAll('input[type="checkbox"]:checked');
                
                for(const checkbox of selectedSetlists) {
                    const setId = checkbox.value;
                    await this.client.deleteSetList(setId); 
                    searchCriteriaDisplay.innerText = "Success :)"
                };

                // const searchCriteriaDisplay = document.getElementById('search-criteria-display');

                // searchCriteriaDisplay.a
                searchCriteriaDisplay.innerText = "Success :)"
                

                await this.clientLoaded();
                searchCriteriaDisplay.innerText = "";
            }

        });
    }    

    
}




const main = async () => {
    const mySetLists = new MySetLists();
    mySetLists.mount();
};

window.addEventListener('DOMContentLoaded', main);