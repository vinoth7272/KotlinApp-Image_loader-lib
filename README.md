# KotlinApp-Image_loader-lib
This app has been created for viewing a particular Reddit image using the created image loader library.
The UI screen will display the list of images and full screen image.

Features:

- Asynchronous loading of image onto the ImageView
-	Faster loading of images by displaying from memory cache
-	Fast loading of images by using disk level cache

The app has two parts as mentioned below.

-	app 
-	:imageloader (loads image from the library)

 App:
 
The app has been designed with the MVVM architecture pattern. The app displays the images in the recycler view with the help of the created image loader library.  The images listed in the Recycler view (thumbnail) have a low resolution whereas a high resolution image is displayed in full screen when the user clicks on an image from the list. It fetches the JSON data from the server using the Retrofit library and converts the JSON into POJO class by using the GSON library. A dependency injection (Koin library) has been used to create the Retrofit instance. 

Imageloader:

ImageLoader receives a request which contains an ImageView and Image URL.

-	It checks if the image is already available in the Memory cache. In case the image is available, it displays it from the memory cache.
-	In scenarios the image is not available in the Memory cache, the image loader searches if the image is available in the Disk cache. If the image is available, it displays the image from the disk cache.
-	In scenarios where the image is unavailable in both the Memory cache and the Disk cache, the imageloader downloads the image from the network URL and saves it in both the Memory and Disk cache. The same process will be applicable when the image is loaded in the first instance.

Decisions:

The above sequence mentioned under imageloader is followed due to the below reasons.
In Memory cache, data is stored in the application memory and it has faster read rate. This will ensure a good user experience as the response time is quick. But the data will get cleared once the application gets killed. So the search happens in the Disk cache where the data is stored and persisted in the disk but it has slower read time compared to the Memory cache. Only if the image is unavailable in both the cache, the image will be downloaded from the network URL.

Assumptions:
-	The network URL contains the relevant image and is able to respond swiftly.
-	JSON structure remains unchanged through-out.

Exception Handling:

-	Default image will be displayed from the local drawable folder in case the network URL fails.
