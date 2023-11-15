package com.nashss.se.vendornearme.metrics;

/**
 * Constant values for use with metrics.
 */
public class MetricsConstants {
    public static final String GETPLAYLIST_PLAYLISTNOTFOUND_COUNT = "GetPlaylist.PlaylistNotFoundException.Count";
    public static final String UPDATEPLAYLIST_INVALIDATTRIBUTEVALUE_COUNT =
        "UpdatePlaylist.InvalidAttributeValueException.Count";
    public static final String UPDATEPLAYLIST_INVALIDATTRIBUTECHANGE_COUNT =
        "UpdatePlaylist.InvalidAttributeChangeException.Count";
    public static final String SERVICE = "Service";
    public static final String SERVICE_NAME = "MusicPlaylistService";
    public static final String NAMESPACE_NAME = "U3/MusicPlaylistService";
    public static final String EVENTLIST_EVENTNOTFOUND_COUNT = "GetAllEvents.EventNotFoundException.Count";
    public static final String GETALLVENDORS_VENDORNOTFOUND_COUNT = "GetAllVendors.VendorNotFoundException.Count";
    public static final String CREATEVENDOR_SUCCESS_COUNT = "GetAllVendors.VendorNotFoundException.Count";
    public static final String CREATEVENDOR_FAIL_COUNT = "GetAllVendors.VendorNotFoundException.Count";
    public static final String EVENT_EVENTNOTFOUND_COUNT = "GetOneEvent.EventNotFoundException.Count";
    public static final String GETVENDOR_VENDORNOTFOUND_COUNT = "GetVendor.VendorNotFoundException.Count";
}
