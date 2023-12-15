import JumanjiJamzClient from '../api/JumanjiJamzClient';
import Header from "../components/header";
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

/**
 * Logic needed to get and View SetList
 */
class ViewSetList extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'addSetListToPage', 'redirectToUpdateSetList'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addSetListToPage);
        this.header = new Header(this.dataStore);
        console.log("viewSetlist constructor");

    }

    /**
     * Once client is loaded, get the Setlist and its metaData
     */
    async clientLoaded() {
        this.client = new JumanjiJamzClient();
        const urlParams = new URLSearchParams(window.location.search);
        const setListId = encodeURI(urlParams.get('id'));
        document.getElementById('setList-name').innerText = "Loading SetList ..."
        console.log(process.env.API_LOCATION);

        const setList = await this.client.getSetList(setListId);
        this.dataStore.set('setList', setList);

        console.log("setList {}", setList);
        
    }

    /**
     * Add the header to the page
     */
    mount() {
        // document.getElementById('add-to-setList').addEventListener('click', this.updateChart);
        document.getElementById('update-setList').addEventListener('click', this.redirectToUpdateSetList);
        this.header.addHeaderToPage();

        this.clientLoaded();
    }

    /**
     * when setList is uupdated in datastore, it displays on page.
     */

    async addSetListToPage() {
        const setList = this.dataStore.get('setList')
        if (setList == null) {
            return;
        }

        document.getElementById('setList-name').innerText = setList.name;
        document.getElementById('setList-owner').innerText = setList.madeBy;
        

        let tagHtml = '';
        let tag;
        for( tag  of setList.genres) {
            tagHtml += '<div class="tag">' + tag + '</div>';
        }
        document.getElementById('tags').innerHTML = tagHtml;
    }

    /**
     * When the update is selected, redirect to the view Setlist page.
     */
    redirectToUpdateSetList() {
        const id = this.dataStore.get('setList').id;
        if (id != null) {
            window.location.href = `/updateSetList.html?id=${id}`;
        }
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const viewSetList = new ViewSetList();
    viewSetList.mount();
};

window.addEventListener('DOMContentLoaded', main);