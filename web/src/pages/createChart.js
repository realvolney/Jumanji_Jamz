import JumanjiJamzClient from '../api/JumanjiJamzClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

/**
 * Logic needed for the create setList page of the website.
 */
class CreateChart extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'submit', 'redirectToViewChart'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.redirectToViewChart);
        this.header = new Header(this.dataStore);
    }

    /**
     * Add the header to the page and load the JumanjiJamzClient
     */
    mount() {
        document.getElementById('create').addEventListener('click', this.submit);

        this.header.addHeaderToPage();

        this.client = new JumanjiJamzClient();
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

        const createButton = document.getElementById('create');
        const origButtonText = createButton.innerText;
        createButton.innerText = 'Loading...';

        const chartName = document.getElementById('chart-name').value;
        const artist = document.getElementById('artist').value;

        // need to check to make sure to style this later 
        const bpm = document.getElementById('bpm').value;
        if (isNaN(bpm)) {
            // The value is not numeric
            this.displayWarning('BPM must be a numeric value.');
            createButton.innerText = 'Create Chart';
        }
        else {

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
        
        const data = await this.client.createChart(chartDetails, (error) => {
            createButton.innerText = origButtonText;
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        });
        this.dataStore.set('data', data);
        }   
    }

    /**
     * When the chart is updated in the datastore, redirect to the view chart page.
     */
    redirectToViewChart() {
        const chart = this.dataStore.get('data');
        if (chart != null) {
            window.location.href = `/chart.html?id=${chart.id}`;
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
    const createChart = new CreateChart();
    createChart.mount();
};

window.addEventListener('DOMContentLoaded', main);
