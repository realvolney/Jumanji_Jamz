import JumanjiJamzClient from '../api/JumanjiJamzClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

/**
 * Logic needed for the create setList page of the website.
 */
class UpdateSetList extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'clientLoaded', 'submit', 'redirectToViewSetList'], this);
        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);
    }
     /**
     * Once client is loaded, get the SetList Id 
     */
     async clientLoaded() {
        this.client = new JumanjiJamzClient();
        const urlParams = new URLSearchParams(window.location.search);
        const setListId = urlParams.get('id');
        
        document.getElementById('setList-name').innerText = "Loading Chart ..."
        this.dataStore.set('id', setListId); 
    }


    /**
     * Add the header to the page and load the JumanjiJamzClient
     */
    mount() {
        document.getElementById('update').addEventListener('click', this.submit);

        this.header.addHeaderToPage();

        this.client = new JumanjiJamzClient();
        this.clientLoaded();
        this.dataStore.addChangeListener(this.redirectToViewSetList);
    }

    /**
     * Method to run when the Update Setlist submit button is pressed. Call the JumanjiJamzService to create the
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

        const setListName = document.getElementById('setList-name').value;
        if (!setListName) {
            this.displayWarning('name must not be blank.');
            updateButton.innerText = 'Update SetList';
            return;
        }
        
        const tagsText = document.getElementById('tags').value;
    
        let tags;
        if (tagsText.length < 1) {
            tags = null;
        } else {
            tags = tagsText.split(/\s*,\s*/);
        }

        const setListDetails = {
            name: setListName,
            genres: tags,
        };

        console.log("payload {}", setListDetails);
        const id = this.dataStore.get('id');
        
        const data = await this.client.updateSetList(id, setListDetails, (error) => {
            updateButton.innerText = origButtonText;
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        });
        this.dataStore.set('data', data);
         
    }

    /**
     * When the setlist is updated in the datastore, redirect to the view setlist page.
     */
    redirectToViewSetList() {
        const id = this.dataStore.get('id');
        if (id != null) {
            window.location.href = `/setlist.html?id=${id}`;
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
    const updateSetList = new UpdateSetList();
    updateSetList.mount();
};

window.addEventListener('DOMContentLoaded', main);
