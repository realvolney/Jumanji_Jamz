# Design Document

## Jumanji Jamz

## 1. Problem Statement

Managing music charts during gigs downtown has been a challenge for our band. We need an app to organize, add, and edit charts seamlessly. This app will centralize all our charts, ensuring easy access from any device, making our performances smoother and more professional.

## 2. Top Questions to Resolve

1. How many tables we need
2. Design for UI
3. How frequently should we cache to  save time in between songs (create a Set Order?)

## 3. Use Cases

U1. _As a user I want to be able to search for music charts_
U2. _As a user I would Like to be able to change the key of the song_
U3. _As a user I would like to make a setList_
U4. _As a user I would like to edit the charts_
U5. _As a User Log in to save setLists_
U6. _As a user I would like to be able to browse available music charts_
U7. _As a user I would like to add a chart_
U8. _As a user I'd like to see charts sorted by genre_
U9. _As a user I'd like to organize how to display the charts_
U10. _As a user I would like tgo modify setList_

Stretch goal:
- Link songs to playable versions on youtube/ spotify etc.
- Be able to grab the bpm from song by listening
- The setList endpoints may be stretch goals

## 4. Project Scope

### 4.1 In Scope

- _searching any song at all_
- _adding and converting to PDF_

### 4.2 Out of Scope

- _having a group account to modify in real time_
- _leave comments on pdfs_
- _use draw tools  on charts_


## 5. Proposed Architecture Overview

_I will use API Gateway and Lambda to create endpoints: GetAllCharts, GetOneChart, CreateChart, UpdateChart, CreateSetList, GetOneSetList, AddToSetList, RemoveFromSetList, ConvertChartToPDF, ConvertSetListToPDF, and GetALLSetLists_

_I will store the Songs in a DynamoDBTable and use GSIs to organize them_

_Will provide a user interface for users to view, edit. and add charts_

## 6. API

### 6.1 Public Models

```
// Chart Model
UUID id;
String name;
String artist;
Integer bpm;
String content;
Set<Strings> genres

// SetList Model
UUID id;
String name;
Set<String> charts
Set<String> genres
```

### 6.2 Get All Charts Endpoint

* Accepts `GET` request to `/musicCharts`
* Scan `Charts` table and return all ChartModel
* If no charts found, will throw `ChartNotFoundException`

![getAllCharts.png](Images%2Fapi_design_images%2FgetAllCharts.png)

### 6.3 Get One Chart Endpoint

* Accepts `GET` request to `/chart/:id`
* Accepts chart `ID` and Queries for specific chart and returns ChartModel
* If chart not found, will throw `ChartNotFoundException`

![getChart.png](Images%2Fapi_design_images%2FgetChart.png)

### 6.4 Create Chart Endpoint

* Accepts `POST` request to `/chart/:id` 
* Will contain optional body to post other fields : "`name`, `artist`, `bpm`, `content`, `genres`"
* Accepts chart `ID` and adds the chart to the `Charts` table
  * `ID` will be generated using java's `UUID` class
* If chart name/artist/Content contains invalid characters: `" ' \ `, InvalidAttributeException will be thrown
* If chart is unable to be added to `Charts` table, will throw `UnableToAddToTableException`

### 6.5 Update Chart Endpoint

* Accepts `PUT` request to `/chart/:id`
* Accepts chart `ID` and overrides old chart on `Charts` table
* Will contain optional body to update fields : "`name`, `artist`, `bpm`, `content`, `genres`"
* If chart name/artist/Content contains invalid characters: `" ' \ `, InvalidAttributeException will be thrown
* If chart is unable to be added to `Charts` table, will throw `UnableToAddToTableException`

![updateChart.png](Images%2Fapi_design_images%2FupdateChart.png)

### 6.6 Create SetList Endpoint

* Accepts `POST` request to `/setList/:id`
* Will contain optional body to post other fields : "`name`, `genres`, `charts`"
* Accepts setList `ID` and adds to `SetLists` table
  * `ID` will be generated using java's `UUID` class
* If setList name//Content contains invalid characters: `" ' \ `, InvalidAttributeException will be thrown
* If setList is unable to be added to `SetLists` table, will throw `UnableToAddToTableException`

![createSetList.png](Images%2Fapi_design_images%2FcreateSetList.png)

### 6.7 Get one SetList  Endpoint

* Accepts `GET` request to `/setlist/:id`
* Accepts setList `ID` and Queries for specific setList and returns SetListModel
* If setList not found, will throw `SetListNotFoundException`

### 6.8 Add to SetList Endpoint

* Accepts `POST` request to `/setList/:id/chart/:chartId`
* Accepts setList `ID` and chart `ID` and adds chart to `SetLists` table
* Will check to make sure chart exists in `Charts` table
* If chart is unable to be added to `SetLists` table, will throw `UnableToAddToTableException`

![addToSetList.png](Images%2Fapi_design_images%2FaddToSetList.png)

### 6.9 Remove from SetList Endpoint

* Accepts `PUT` request to `/setList/:id/chart/:chartId`
* Accepts setList `ID` and chart `ID` and removes chart from `SetLists` table
* Will update entry
* Will check to make sure chart exists in `Charts` table
* If chart is unable to be added to `SetLists` table, will throw `UnableToAddToTableException`

// Unsure whether I need a model for PDF if I am storing them in an S3 bucket?
### 6.10 Convert Chart to PDF Endpoint

* Accepts `GET` request to `/pdf/chart/:id`
* Accepts chart `ID` and Queries for specific chart and returns PDF of chart content
* If chart not found, will throw `ChartNotFoundException`

### 6.11 Convert SetList to PDF Endpoint

* Accepts `GET` request to `/pdf/setList/:id`
* Accepts setList `ID` and Queries for specific chart and returns PDF of setList charts content
* If setList not found, will throw `SetListNotFoundException`

![convertSetList.png](Images%2Fapi_design_images%2FconvertSetList.png)

// Wasn't sure if this should be an endpoint or not
### 6.12 Search MusicCharts By name Endpoint 

* Accepts `GET` request to `/chart/:name`
* Scan `Charts` GSI table and return chart with same name
* Accepts chart `name` and Queries for specific chart and returns ChartModel
* If chart not found, will throw `ChartNotFoundException`

### 6.13 Search SetList By name Endpoint 

* Accepts `GET` request to `/setList/:name`
* Scan `SetLists` GSI table and return setList with same name
* Accepts setList `name` and Queries for specific setList and returns SetListModel
* If setList not found, will throw `SetListNotFoundException`

### 6.14 Get All SetLists Endpoint

* Accepts `GET` request to `/setLists`
* Scan `SetLists` table and return all SetListModels
* If no setList found, will throw `SetListNotFoundException`

## 7. Tables

### 7.1 `Charts`

```
id // partition key,S: string
name // S: string
artist // S: string
bpm // N: number
content // S: string
genres // SS: Set of Strings
```

### 7.2 `SetList`

```
id // partition key, S: string
name // S: string
charts // SS: set of Strings
genres // SS: Set of Strings
```

# 8. Pages

![webPage1.png](Images%2Fdesign_images%2FwebPage1.png)

![webPage2.png](Images%2Fdesign_images%2FwebPage2.png)
 





