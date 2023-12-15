import JumanjiJamzClient from '../api/JumanjiJamzClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

/**
 * Logic needed for the create setList page of the website.
 */
class UpdateChart extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'clientLoaded', 'submit', 'redirectToViewChart'], this);
        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);
    }
     /**
     * Once client is loaded, get the Chart and its metaData
     */
     async clientLoaded() {
        this.client = new JumanjiJamzClient();
        const urlParams = new URLSearchParams(window.location.search);
        const chartId = urlParams.get('id');
        
        document.getElementById('chart-name').innerText = "Loading Chart ..."
        this.dataStore.set('id', chartId); 
    }


    /**
     * Add the header to the page and load the JumanjiJamzClient
     */
    mount() {
        document.getElementById('update').addEventListener('click', this.submit);

        this.header.addHeaderToPage();

        this.client = new JumanjiJamzClient();
        this.clientLoaded();
        this.dataStore.addChangeListener(this.redirectToViewChart);
    }

    /**
     * Method to run when the create chart submit button is pressed. Call the JumanjiJamzService to create the
     * chart.
     */
    async submit(evt) {
        evt.preventDefault();

        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');

        const updateButton = document.getElementById('update');
        const origButtonText = updateButton.innerText;
        updateButton.innerText = 'Loading...';

        const chartName = document.getElementById('chart-name').value;
        if (!chartName) {
            this.displayWarning('name must not be blank.');
            updateButton.innerText = 'Update Chart';
            return;
        }
        const artist = document.getElementById('artist').value;

        // need to check to make sure to style this later 
        const bpm = document.getElementById('bpm').value;
        if (isNaN(bpm)) {
            // The value is not numeric
            this.displayWarning('BPM must be a numeric value.');
            updateButton.innerText = 'Update Chart';
            return;
        }
        
        const content = document.getElementById('content').value;
        const tagsText = document.getElementById('tags').value;
    
        
        let tags;
        if (tagsText.length < 1) {
            tags = null;
        } else {
            tags = tagsText.split(/\s*,\s*/);
        }

        const chartDetails = {
            name: chartName,
            artist: artist,
            bpm: bpm,
            content: content,
            genres: tags,
        };

        console.log("payload {}", chartDetails);
        const id = this.dataStore.get('id');
        
        const data = await this.client.updateChart(id, chartDetails, (error) => {
            updateButton.innerText = origButtonText;
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        });
        this.dataStore.set('data', data);
         
    }

    /**
     * When the chart is updated in the datastore, redirect to the view chart page.
     */
    redirectToViewChart() {
        const id = this.dataStore.get('id');
        if (id != null) {
            window.location.href = `/chart.html?id=${id}`;
        }
    }
    
    displayWarning(message) {
        const warningDiv = document.createElement('div');
        warningDiv.textContent = message;
        warningDiv.classList.add('warning');
        document.body.appendChild(warningDiv);
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const updateChart = new UpdateChart();
    updateChart.mount();
};

window.addEventListener('DOMContentLoaded', main);
