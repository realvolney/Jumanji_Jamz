import JumanjiJamzClient from "../api/JumanjiJamzClient";
import Header from "../components/header";
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

/**
 * Logic needed to get and View Chart
 */
class ViewChart extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount']);
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



}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const viewChart = new ViewChart();
    viewPlaylist.mount();
};

window.addEventListener('DOMContentLoaded', main);