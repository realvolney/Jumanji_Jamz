
import Header from "../components/header";
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

/**
 * Logic needed to get and View Chart
 */
class ViewChart extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'addChartToPage']);
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
        document.getElementById()
        
    }

    /**
     * Add the header to the page
     */
    mount() {
        document.getElementById('update-chart').addEventListener('click', this.updateChart);
        this.header.addHeaderToPage();

        this.clientLoaded();
    }

    /**
     * when chart is uupdated in datastore, it displays on page.
     */

    async addChartToPage() {
        const chart = this.dataStore.get('chart')
        if (chart == null) {
            return;
        }

        document.getElementById('chart-name').innerText = chart.name;
        document.getElementById('chart-owner').innerText = chart.madeBy;

        let tagHtml = '';
        let tag;
        for( tag  of chart.genres) {
            tagHtml += '<div class="tag">' + tag + '</div>';
        }
        document.getElementById('tags').innerHTML = tagHtml;
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