package com.sadma.example.nearbyservices.utils

/**
 * Created by Sadman on 2018-07-29.
 */
object GoogleApiUrl {
    /**
     * Google Api Url Constants
     */
    const val BASE_URL = "https://maps.googleapis.com/maps/api/place/"
    const val NEARBY_SEARCH_TAG = "nearbysearch"
    const val JSON_FORMAT_TAG = "json"
    const val LOCATION_TAG = "location"
    const val RADIUS_TAG = "radius"
    const val RADIUS_VALUE = "5000"
    const val PLACE_TYPE_TAG = "type"
    const val NEXT_PAGE_TOKEN_TAG = "pagetoken"
    const val API_KEY_TAG = "key"
    const val RANK_BY_TAG = "rankby"
    const val DISTANCE_TAG = "distance"
    const val KEYWORD_TAG = "keyword"
    const val LOCATION_DETAIL_TAG = "details"
    const val LOCATION_PLACE_ID_TAG = "placeid"
    const val API_KEY = "AIzaSyARyyRZ0oSxvFT5261VNUcS5vLDrvt07SQ"

    /**
     * Intent data transfer key
     */
    const val LOCATION_TYPE_EXTRA_TEXT = "location_type"
    const val LOCATION_NAME_EXTRA_TEXT = "location_name"
    const val ALL_NEARBY_LOCATION_KEY = "all_nearby_location_key"
    const val LOCATION_ID_EXTRA_TEXT = "location_id"
    const val CURRENT_LOCATION_DATA_KEY = "current_location_data"
    const val CURRENT_LOCATION_SHARED_PREFERENCE_KEY = "shared_preference_key"
    const val CURRENT_LOCATION_USER_RATING_KEY = "current_location_user_rating"
}