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

        const chartName = document.getElementById('setList-name').value;
        const tagsText = document.getElementById('tags').value;
    

        let tags;
        if (tagsText.length < 1) {
            tags = null;
        } else {
            tags = tagsText.split(/\s*,\s*/);
        }

        const setListDetails = {
            name: setListName,
            charts: null,
            genres: tags
        };

        console.log("payload {}", setListDetails);
        
        const data = await this.client.createSetList(setListDetails, (error) => {
            createButton.innerText = origButtonText;
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        });
        this.dataStore.set('data', data);
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
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const ccreateChart = new CreateChart();
    createChart.mount();
};

window.addEventListener('DOMContentLoaded', main);
