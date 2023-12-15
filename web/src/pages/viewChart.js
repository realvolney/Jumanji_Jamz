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
        this.bindClassMethods(['clientLoaded', 'mount', 'addChartToPage', 'redirectToUpdateChart'], this);
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
        const id = this.dataStore.get('chart').id;
        if (id != null) {
            window.location.href = `/updateChart.html?id=${id}`;
        }
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