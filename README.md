# Project 2 - *NY Times Search*

**Name of your app** is an android app that allows a user to search for articles on web using simple filters. The app utilizes [New York Times Search API](http://developer.nytimes.com/docs/read/article_search_api_v2).

Time spent: **20** hours spent in total

## User Stories

The following **required** functionality is completed:

* [Implemented] User can **search for news article** by specifying a query and launching a search. Search displays a grid of image results from the New York Times Search API.
* [Implemented] User can click on "settings" which allows selection of **advanced search options** to filter results
* [Implemented] User can configure advanced search filters such as:
  * [Implemented] Begin Date (using a date picker)
  * [Implemented] News desk values (Arts, Fashion & Style, Sports)
  * [Implemented] Sort order (oldest or newest)
* [Implemented] Subsequent searches have any filters applied to the search results
* [Implemented] User can **scroll down to see more articles**. The maximum number of articles is limited by the API search.

The following **optional** features are implemented:

* [Implemented] Used the **ActionBar SearchView** or custom layout as the query box instead of an EditText
* [Implemented] Improved the user interface and experiment with image assets and/or styling and coloring

The following **bonus** features are implemented:

* [Implemented] Use the [RecyclerView](http://guides.codepath.com/android/Using-the-RecyclerView) with the `StaggeredGridLayoutManager` to display improve the grid of image results
* [Implemented] Use Parcelable instead of Serializable using the popular [Parceler library](http://guides.codepath.com/android/Using-Parceler).

## Video Walkthrough

Here's a walkthrough of implemented user stories:[Video Walkthrough](http://i.imgur.com/2uEi8xi.gif)

Here a new Release Including de Web View to hit an article and look the web page of it
<img src='http://i.imgur.com/sYbOmR5.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Open-source libraries used

- [Android Async HTTP](https://github.com/loopj/android-async-http) - Simple asynchronous HTTP requests with JSON parsing
- [Picasso](http://square.github.io/picasso/) - Image loading and caching library for Android

## License

    Copyright [yyyy] [name of copyright owner]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
