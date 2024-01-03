import DataStore from '../util/DataStore';
import BindingClass from "../util/bindingClass";
import JumanjiJamzClient from "../api/JumanjiJamzClient";
import Header from '../components/header';
import Authenticator from '../api/authenticator';

class MySetLists extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['showLoading', 'hideLoading', 'clientLoaded', 'mount', 'displaySetlists', 'getHTMLForSetListResults'], this);
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
        const setLists = await this.client.mySetLists();
        const info = await this.authenticator.getCurrentUserInfo();
        console.log("info {}", info);
        const user = document.getElementById('setList-owner');
        user.innerText = info.name + "'s Setlists";
        this.hideLoading();

        console.log("SetLists {}", setLists);


        this.dataStore.set('setLists', setLists);
        this.displaySetlists();

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
    }

    

    getHTMLForSetListResults(setLists) {
        if (!setLists.length) {
            return '<h4>You have no setlists</h4>';
        }

        let html = '<table><tr><th>Name</th><th>Genres</th></tr>';
        for (const res of setLists) {
            html += `
            <tr>
                <td>
                    <a href="setlist.html?id=${res.id}">${res.name}</a>
                </td>
        
                <td>${res.genres ? res.genres?.join(', ') : 'none'}</td>
            </tr>`;
        }
        html += '</table>';

        return html;
    }
}




const main = async () => {
    const mySetLists = new MySetLists();
    mySetLists.mount();
};

window.addEventListener('DOMContentLoaded', main);