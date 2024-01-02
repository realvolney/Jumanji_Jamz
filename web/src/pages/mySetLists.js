import DataStore from '../util/DataStore';
import BindingClass from "../util/bindingClass";
import JumanjiJamzClient from "../api/JumanjiJamzClient";
import Header from '../components/header';

class MySetLists extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['showLoading', 'hideLoading', 'clientLoaded', 'mount', 'displaySetlists', 'getHTMLForSetListResults'], this);
        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);
        this.client = new JumanjiJamzClient();
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
        // const displayDiv = document.getElementById('charts-list-display');
        // displayDiv.innerText = charts.length > 0 ? "" : "No more Charts available.";
    
    
    
        // charts.forEach(chart => {
        //         const chartCard = document.createElement('section');
        //         chartCard.className = 'chartCard';
    
        //         const chartId = encodeURIComponent(chart.id);
                
    
        //         const currentHostname = window.location.hostname;
    
        //         const isLocal = currentHostname === 'localhost' || currentHostname === '127.0.0.1';
        //         const baseUrl = isLocal ? 'http://localhost:8000/' : 'https://d1pv9h2o6o7zp8.cloudfront.net/';
    
        //         const chartPageUrl = `${baseUrl}chart.html?id=${chartId}`;
    
        //         const chartName = document.createElement('h2');
        //         chartName.innerText = chart.name;
    
        //         const chartMadeBy = document.createElement('h3');
        //         chartMadeBy.innerText = chart.madeBy;
    
        //         chartCard.appendChild(chartName);
        //         chartCard.appendChild(chartMadeBy);
    
        //         displayDiv.appendChild(chartCard);
    
        //         chartCard.addEventListener('click', () => {
        //             window.location.href = chartPageUrl;
        //             console.log('Created Event listener' + chartPageUrl);
        //         });
        // });
    }

    

    getHTMLForSetListResults(searchResults) {
        if (searchResults.length == 0) {
            return '<h4>No results found</h4>';
        }

        let html = '<table><tr><th>Name</th><th>Created by</th><th>Genres</th></tr>';
        for (const res of searchResults) {
            html += `
            <tr>
                <td>
                    <a href="setlist.html?id=${res.id}">${res.name}</a>
                </td>
                <td>${res.madeBy}</td>
                <td>${res.genres?.join(', ')}</td>
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