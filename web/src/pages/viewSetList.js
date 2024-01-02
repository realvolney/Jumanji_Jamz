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
        this.bindClassMethods(['clientLoaded', 'mount', 'addSetListToPage', 'redirectToUpdateSetList',
        'displayCharts','getHTMLForChartResults'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addSetListToPage);
        this.dataStore.addChangeListener(this.displayCharts);
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
        this.dataStore.set('charts', setList.charts);

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
        if (!setList.genres) {
        for( tag  of setList.genres) {
            tagHtml += '<div class="tag">' + tag + '</div>';
        }
        }
        document.getElementById('tags').innerHTML = tagHtml;
    }

    /**
     * When the update is selected, redirect to the view Setlist page.
     */
    redirectToUpdateSetList() {
        const setList = this.dataStore.get('setList');
        if (setList != null) {
            window.location.href = `/updateSetList.html?id=${setList.id}`;
        }
    }

    displayCharts() {
        const charts = this.dataStore.get('charts');
        console.log('charts {}', charts);
         const setListsResultsContainer = document.getElementById('search-results-container');
       
        const setListsResultsDisplay = document.getElementById('search-results-display');

    
            
       
            setListsResultsDisplay.innerHTML = this.getHTMLForChartResults(charts);    
    }

    

    getHTMLForChartResults(charts) {
        if (!charts) {
            return '<h4>This setlist contains no charts</h4>';
        }

        let html = '<table><tr><th>Name</th><th>Genres</th></tr>';
        for (const res of charts) {
            html += `
            <tr>
                <td>
                    <a href="setlist.html?id=${res.id}">${res.name}</a>
                </td>
        
                <td>${res.genres ? res.genres?.join(', ') : 'none'}</td>
            </tr>`;
        }
        html += '</table>';

        return html;
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