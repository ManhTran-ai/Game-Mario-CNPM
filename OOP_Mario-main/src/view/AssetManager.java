package view;

public class AssetManager {

    private static AssetManager instance;
    private final ImageLoader imageLoader;

    private AssetManager() {
        imageLoader = new ImageLoader();
    }

    public static AssetManager getInstance() {
        if (instance == null) {
            instance = new AssetManager();
        }
        return instance;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }
}
