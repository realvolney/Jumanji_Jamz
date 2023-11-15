package capstone.converters;



import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Converts between Data and API models.
 */
public class ModelConverter {
//    private ZoneDateTimeConverter converter = new ZoneDateTimeConverter();
//    /**
//     * Converts a provided {@link Playlist} into a {@link PlaylistModel} representation.
//     *
//     * @param playlist the playlist to convert
//     * @return the converted playlist
//     */
//    public PlaylistModel toPlaylistModel(Playlist playlist) {
//        List<String> tags = null;
//        if (playlist.getTags() != null) {
//            tags = new ArrayList<>(playlist.getTags());
//        }
//
//        return PlaylistModel.builder()
//                .withId(playlist.getId())
//                .withName(playlist.getName())
//                .withCustomerId(playlist.getCustomerId())
//                .withCustomerName(playlist.getCustomerName())
//                .withSongCount(playlist.getSongCount())
//                .withTags(tags)
//                .build();
//    }
//
//    /**
//     * Converts a provided AlbumTrack into a SongModel representation.
//     *
//     * @param albumTrack the AlbumTrack to convert to SongModel
//     * @return the converted SongModel with fields mapped from albumTrack
//     */
//    public SongModel toSongModel(AlbumTrack albumTrack) {
//        return SongModel.builder()
//                .withAsin(albumTrack.getAsin())
//                .withTrackNumber(albumTrack.getTrackNumber())
//                .withAlbum(albumTrack.getAlbumName())
//                .withTitle(albumTrack.getSongTitle())
//                .build();
//    }
//    /**
//     * Converts a provided Vendor into VendorModel representation.
//     *
//     * @param vendor the Vendor to convert to VendorModel
//     * @return the converted VendorModel with fields mapped from vendor
//     */
//    public VendorModel toVendorModel(Vendor vendor) {
//        Set<String> eventIds = null;
//        Set<String> tags = null;
//        if (vendor.getEventIds() != null) {
//            eventIds = new HashSet<>(vendor.getEventIds());
//        }
//        if (vendor.getTags() != null) {
//            tags = new HashSet<>(vendor.getTags());
//        }
//        return VendorModel.builder()
//                .withId(vendor.getId())
//                .withName(vendor.getName())
//                .withBio(vendor.getBio())
//                .withEventIds(eventIds)
//                .withTags(tags)
//                .build();
//    }
//
//    /**
//     * Converts a list of Vendors to a list of VendorModels.
//     *
//     * @param vendors The Vendors to convert to VendorModels
//     * @return The converted list of VendorModels
//     */
//    public List<VendorModel> toVendorModelList(List<Vendor> vendors) {
//        List<VendorModel> vendorModels = new ArrayList<>();
//        vendors.forEach(vendor -> vendorModels.add(toVendorModel(vendor)));
//        return vendorModels;
//    }
//
//
//    /**
//     * Converts a list of AlbumTracks to a list of SongModels.
//     *
//     * @param albumTracks The AlbumTracks to convert to SongModels
//     * @return The converted list of SongModels
//     */
//    public List<SongModel> toSongModelList(List<AlbumTrack> albumTracks) {
//        List<SongModel> songModels = new ArrayList<>();
//
//        for (AlbumTrack albumTrack : albumTracks) {
//            songModels.add(toSongModel(albumTrack));
//        }
//
//        return songModels;
//    }
//
//    /**
//     * Converts a list of Playlists to a list of PlaylistModels.
//     *
//     * @param playlists The Playlists to convert to PlaylistModels
//     * @return The converted list of PlaylistModels
//     */
//    public List<PlaylistModel> toPlaylistModelList(List<Playlist> playlists) {
//        List<PlaylistModel> playlistModels = new ArrayList<>();
//
//        for (Playlist playlist : playlists) {
//            playlistModels.add(toPlaylistModel(playlist));
//        }
//
//        return playlistModels;
//    }
//    /**
//     * Converts a provided {@link Event} into a {@link EventModel} representation.
//     *
//     * @param event the event to convert
//     * @return the converted event
//     */
//    public EventModel toEventModel(Event event) {
//        Set<String> tags = null;
//        if (event.getTags() != null) {
//            tags = new HashSet<>(event.getTags());
//        }
//        Set<String> vendorList = null;
//        if (event.getVendorList() != null) {
//            vendorList = new HashSet<>(event.getVendorList());
//        }
//
//        return EventModel.builder()
//                .withId(event.getId())
//                .withName(event.getName())
//                .withDate(converter.convert(event.getDate()))
//                .withIsActive(event.getIsActive())
//                .withLocation(event.getLocation())
//                .withPrice(event.getPrice())
//                .withVendorList(vendorList)
//                .withTags(tags)
//                .build();
//    }
//    /**
//     * Converts a list of Event to a list of EventModels.
//     *
//     * @param events The Event to convert to EventModels
//     * @return The converted list of EventModels
//     */
//    public List<EventModel> toEventModelList(List<Event> events) {
//        List<EventModel> eventModels = new ArrayList<>();
//
//        for (Event event: events) {
//            eventModels.add(toEventModel(event));
//        }
//        return eventModels;
//    }
}
