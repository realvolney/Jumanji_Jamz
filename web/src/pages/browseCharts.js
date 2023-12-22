import { DataStore } from "aws-amplify";
import BindingClass from "../util/bindingClass";
import JumanjiJamzClient from "../api/JumanjiJamzClient";

class BrowseCharts extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['showLoading', 'hideLoading', 'clientLoaded', 'mount', 'next', 'previous', 'displayCharts'], this);
        this.dataStore = new DataStore();
        this.client = new JumanjiJamzClient();
        this.dataStore.addChangeListener(this.displayCharts);
        this.previousKeys = [];
        console.log("BrowseCharts constructor");
        this.limit = 5;
    }

    showLoading() {
        document.getElementById('loading').innerText = "Loading Charts";
    }

    hideLoading() {
        document.getElementById('loading').style.display = 'none';
    }

    async clientLoaded() {
        this.showLoading();
        const result = await this.client.getAllCharts(null, this.limit);
        this.hideLoading();

        console.log("Result {}", result);

        this.previousKeys.push(result.currentId);

        this.dataStore.set('charts', result.charts);
        this.dataStore.set('previousId', result.currentId)
        this.dataStore.set('nextId', result.nextId);
        this.displayCharts();
    }

    /**
     * Add the header to the page and load the VendorEventClient.
     */
    mount() {
        this.header.addHeaderToPage();
        this.clientLoaded();
        document.getElementById('nextButton').addEventListener('click', this.next);
        document.getElementById('prevButton').addEventListener('click', this.previous);
    }

    async next() {
        this.showLoading();
         // To make the it stop at the end rather than looping around
         if (this.dataStore.get('charts') == 0) {
             this.displayCharts();
         }
         else {
         const nextId = this.dataStore.get('nextId');
         const nextLimit = this.dataStore.get('nextLimit');
     
         const result = await this.client.getAllVendors(nextId, this.limit);
         console.log("Result:", result);
         
         console.log("Received charts:", result.charts);
     
         this.previousKeys.push(this.dataStore.get('previousId'));
     
         this.dataStore.set('charts', result.charts);
         this.dataStore.set('previousId', result.currentId)
         this.dataStore.set('nextId', result.nextId);
         this.hideLoading();
         }
    }

    async previous() {
        this.showLoading();
    
        let result;
            if (this.previousKeys.length > 0) {
                const previousId = this.previousKeys.pop();
                result = await this.client.getAllVendors(previousId, limit);
            } else {
                result = await this.client.getAllVendors(this.dataStore.get('previousId'), limit);
            }
    
        console.log("Result:", result);
        console.log("Received charts:", result.charts);
    
        
        this.dataStore.set('charts', result.charts);
        this.dataStore.set('previousId', result.currentId)
        this.dataStore.set('nextId', result.nextId);
        this.hideLoading();
    }

    displayCharts() {
        const vendors = this.dataStore.get('vendors');
        const displayDiv = document.getElementById('vendors-list-display');
        displayDiv.innerText = vendors.length > 0 ? "" : "No more Vendors available.";
    
    
    
        vendors.forEach(vendor => {
                const chartCard = document.createElement('section');
                chartCard.className = 'vendorCard';
    
                const vendorId = encodeURIComponent(vendor.id);
                const name = encodeURIComponent(vendor.name);
    
                const currentHostname = window.location.hostname;
    
                const isLocal = currentHostname === 'localhost' || currentHostname === '127.0.0.1';
                const baseUrl = isLocal ? 'http://localhost:8000/' : 'https://d3hqn9u6ae71hc.cloudfront.net/';
    
                const vendorPageUrl = `${baseUrl}viewVendor.html?id=${vendorId}&name=${encodeURIComponent(name)}`;
    
                const vendorName = document.createElement('h2');
                vendorName.innerText = vendor.name;
    
                const vendorBio = document.createElement('h3');
                vendorBio.innerText = vendor.bio;
    
                vendorCard.appendChild(vendorName);
                vendorCard.appendChild(vendorBio);
    
                displayDiv.appendChild(vendorCard);
    
                vendorCard.addEventListener('click', () => {
                    window.location.href = vendorPageUrl;
                    console.log('Created Event listener' + vendorPageUrl);
                });
            });
        }
    }


}

const main = async () => {
    const browseCharts = new BrowseCharts();
    browseCharts.mount();
};

window.addEventListener('DOMContentLoaded', main);