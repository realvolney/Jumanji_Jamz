import JumanjiJamzClient from '../api/JumanjiJamzClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

/**
 * Logic needed for the create setList page of the website.
 */
class CreateSetList extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'submit', 'redirectToViewSetList'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.redirectToViewSetList);
        this.header = new Header(this.dataStore);
    }

    /**
     * Add the header to the page and load the MusicPlaylistClient.
     */
    mount() {
        document.getElementById('create').addEventListener('click', this.submit);

        this.header.addHeaderToPage();

        this.client = new JumanjiJamzClient();
    }

    /**
     * Method to run when the create playlist submit button is pressed. Call the MusicPlaylistService to create the
     * playlist.
     */
    async submit(evt) {
        evt.preventDefault();

        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');

        const createButton = document.getElementById('create');
        const origButtonText = createButton.innerText;
        createButton.innerText = 'Loading...';

        const setListName = document.getElementById('setList-name').value;
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
        
        const setList = await this.client.createSetList(setListDetails, (error) => {
            createButton.innerText = origButtonText;
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        });
        this.dataStore.set('setList', setList);
    }

    /**
     * When the playlist is updated in the datastore, redirect to the view playlist page.
     */
    redirectToViewSetList() {
        const setList = this.dataStore.get('setList');
        if (setList != null) {
            window.location.href = `/setList.html?id=${setList.id}`;
        }
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const createSetList = new CreateSetList();
    createSetList.mount();
};

window.addEventListener('DOMContentLoaded', main);