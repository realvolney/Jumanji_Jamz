import JumanjiJamzClient from '../api/JumanjiJamzClient';
import Header from "../components/header";
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

/**
 * Logic needed to get and View Chart
 */
class ViewChart extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'addChartToPage', 'redirectToUpdateChart',
         'popUpMySetLists', 'getHTMLForSetListResults', 'addChart'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addChartToPage);
        this.header = new Header(this.dataStore);
        console.log("viewChart constructor");

    }

    /**
     * Once client is loaded, get the Chart and its metaData
     */
    async clientLoaded() {
        this.client = new JumanjiJamzClient();
        const urlParams = new URLSearchParams(window.location.search);
        const chartId = urlParams.get('id');
        document.getElementById('chart-name').innerText = "Loading Chart ..."

        const chart = await this.client.getChart(chartId);
        this.dataStore.set('chart', chart);
        
    }

    /**
     * Add the header to the page
     */
    mount() {
        document.getElementById('update-chart').addEventListener('click', this.redirectToUpdateChart);
        document.getElementById('add-to-setList').addEventListener('click', this.popUpMySetLists);
        this.header.addHeaderToPage();

        this.clientLoaded();
    }

    /**
     * when chart is updated in datastore, it displays on page.
     */

    async addChartToPage() {
        const chart = this.dataStore.get('chart')
        if (chart == null) {
            return;
        }

        document.getElementById('chart-name').innerText = chart.name;
        document.getElementById('chart-owner').innerText = chart.madeBy;
        document.getElementById('content').innerText = chart.content;
        document.getElementById('bpm').innerText = chart.bpm;

        let tagHtml = '';
        let tag;
        if (!chart.genres) {
            tag = null;
        }
        else {
            for( tag  of chart.genres) {
                tagHtml += '<div class="tag">' + tag + '</div>';
            }
        }
        document.getElementById('tags').innerHTML = tagHtml;
    }

    /**
     * When the update is selected, redirect to the view chart page.
     */
    redirectToUpdateChart() {
        const chart = this.dataStore.get('chart');
        if (chart != null) {
            window.location.href = `/updateChart.html?id=${chart.id}`;
        }
    }

     /**
     * When the Add to SetList is selected, redirect to the mySetlists popUp
     */

    async popUpMySetLists() {
        const addButton = document.getElementById('add-to-setList');
        addButton.innerText = "Loading...";
        const chart = this.dataStore.get('chart');
        console.log('chart {}', chart);

        if (chart != null) {
            const setlists = await this.client.mySetLists();
            console.log('setlists {}', setlists);
            this.dataStore.set('my-setlists', setlists);


        
            const setListsResultsContainer = document.getElementById('search-results-container');
            const setListsResultsDisplay = document.getElementById('search-results-display');
            const searchCriteriaDisplay = document.getElementById('search-criteria-display');

            searchCriteriaDisplay.innerText = "My Setlists"
            setListsResultsContainer.classList.remove('hidden');
       
            setListsResultsDisplay.innerHTML = this.getHTMLForSetListResults(setlists);   
        } 
        addButton.innerText = "Add to SetList";
    }
    getHTMLForSetListResults(setLists) {
        if (!setLists) {
            return '<h4>You have no setlists</h4>';
        }
        
        const chart = this.dataStore.get('chart');
        console.log('chart {}', chart);
        let html = '<table><tr><th>Name</th><th>Genres</th></tr>';
        for (const res of setLists) {
            html += `
            <tr>
                <td>
                    <a href="setlist.html?id=${res.id}">${res.name}</a>
                
                </td>
        
                <td>${res.genres ? res.genres?.join(', ') : 'none'}</td>

                <td>
                <!-- Use a button or a link with an onclick event -->
                <button onclick="this.addChart(${chart}${res.id})">Add Chart</button>
            </td>
            </tr>`;
        }
      
        html += '</table>';

        return html;
        
    }
    async addChart(chart, id) {
        await this.client.addChart(chart, id);
    }
   
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const viewChart = new ViewChart();
    viewChart.mount();
};

window.addEventListener('DOMContentLoaded', main);