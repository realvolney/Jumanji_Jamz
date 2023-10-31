# Design Document

## Jumanji Charts

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

Stretch goal:
- Link songs to playable versions on youtube/ spotify etc.
- Be able to grab the bpm from song by listening

## 4. Project Scope

### 4.1 In Scope

- _searching any song at all_

### 4.2 Out of Scope

## 5. Proposed Architecture Overview

_I will use API Gateway and Lambda to create endpoints: _

_I will store the Songs in a DynamoDBTable and use GSIs to organize them_

_Will provide a user interface for users to view, edit. and add charts_

## 6. API

## 6.1 Public Models

```
// Chart Model

String name;
String artist;
Integer bpm;


