import DataStore from '../util/DataStore';
import BindingClass from "../util/bindingClass";
import JumanjiJamzClient from "../api/JumanjiJamzClient";
import Header from '../components/header';

class BrowseCharts extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['showLoading', 'hideLoading', 'clientLoaded', 'mount', 'next', 'previous',
        'getHTMLForChartResults', 'displayCharts'], this);
        this.dataStore = new DataStore();
        this.client = new JumanjiJamzClient();
        this.dataStore.addChangeListener(this.displayCharts);
        this.previousKeys = [];
        console.log("BrowseCharts constructor");
        this.limit = 5;
        
    }

    showLoading() {
        document.getElementById('loading').innerText = "Loading Charts";
    }

    hideLoading() {
        document.getElementById('loading').style.display = 'none';
    }

    async clientLoaded() {
        this.showLoading();
        const result = await this.client.getAllCharts(null, this.limit);
        this.hideLoading();

        console.log("Result {}", result);

        this.previousKeys.push(result.currentId);

        this.dataStore.set('charts', result.charts);
        this.dataStore.set('previousId', result.currentId)
        this.dataStore.set('nextId', result.nextId);
        this.displayCharts();
        console.log("nextKey {}", result.nextId);
    }

    /**
     * Add the header to the page and load the VendorEventClient.
     */
    mount() {
        
        this.clientLoaded();
        document.getElementById('nextButton').addEventListener('click', this.next);
        document.getElementById('prevButton').addEventListener('click', this.previous);
    }

    async next() {
        this.showLoading();
         // To make the it stop at the end rather than looping around
         if (this.dataStore.get('charts') == 0) {
             this.displayCharts();
         }
         else {
         const nextId = this.dataStore.get('nextId');

     
         const result = await this.client.getAllCharts(nextId, 4);
         console.log("Result:", result);
         
         console.log("Received charts:", result.charts);
     
         this.previousKeys.push(this.dataStore.get('previousId'));
     
         this.dataStore.set('charts', result.charts);
         this.dataStore.set('previousId', result.currentId)
         this.dataStore.set('nextId', result.nextId);
         this.hideLoading();
         console.log("nextKey {}", result.nextId);
         }
    }

    async previous() {
        this.showLoading();
    
        const previousId = this.previousKeys.pop() || this.dataStore.get('previousId');
        const result = await this.client.getAllCharts(previousId, 4);
    
        console.log("Result:", result);
        console.log("Received charts:", result.charts);
    
        this.dataStore.set('charts', result.charts);
        this.dataStore.set('previousId', result.currentId);
        this.dataStore.set('nextId', result.nextId);
        this.hideLoading();
        console.log("nextKey {}", result.nextId);
    }
    

    displayCharts() {
        const charts = this.dataStore.get('charts');
        const displayDiv = document.getElementById('charts-list-display');
        displayDiv.innerText = charts.length > 0 ? "" : "No more Charts available.";
    
    
    
        charts.forEach(chart => {
                const chartCard = document.createElement('section');
                chartCard.className = 'chartCard';
    
                const chartId = encodeURIComponent(chart.id);
                
    
                const currentHostname = window.location.hostname;
    
                const isLocal = currentHostname === 'localhost' || currentHostname === '127.0.0.1';
                const baseUrl = isLocal ? 'http://localhost:8000/' : 'https://d1pv9h2o6o7zp8.cloudfront.net/';
    
                const chartPageUrl = `${baseUrl}chart.html?id=${chartId}`;
    
                const chartName = document.createElement('h2');
                chartName.innerText = chart.name;
    
                const chartMadeBy = document.createElement('h3');
                chartMadeBy.innerText = chart.madeBy;
    
                chartCard.appendChild(chartName);
                chartCard.appendChild(chartMadeBy);
    
                displayDiv.appendChild(chartCard);
    
                chartCard.addEventListener('click', () => {
                    window.location.href = chartPageUrl;
                    console.log('Created Event listener' + chartPageUrl);
                });
        });
    }

    getHTMLForChartResults(searchResults) {
        

        let html = '<table><tr><th>Name</th><th>Song Count</th><th>Tags</th></tr>';
        for (const res of searchResults) {
            html += `
            <tr>
                <td>
                    <a href="chart.html?id=${res.id}">${res.name}</a>
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
    const browseCharts = new BrowseCharts();
    browseCharts.mount();
};

window.addEventListener('DOMContentLoaded', main);