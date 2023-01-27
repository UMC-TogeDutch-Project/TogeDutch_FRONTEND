package umc.mobile.project.ram.Auth

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import androidx.loader.content.CursorLoader

class RealPathUtil {
    var realPath : String = ""
    fun getRealPath(context : Context, fileUri : Uri) : String{
        val realPathUtil = RealPathUtil()
        if(Build.VERSION.SDK_INT < 11) {
            realPath = realPathUtil.getRealPathFromURI_BelowAPI11(context, fileUri)
        }
        else if(Build.VERSION.SDK_INT < 19){
            realPath = realPathUtil.getRealPathFromURI_API11to18(context, fileUri)
        }
        else{
            realPath = realPathUtil.getRealPathFromURI_API19(context, fileUri)!!
        }

        return realPath
    }

    fun getRealPathFromURI_API11to18(context: Context, contentUri : Uri) : String{
        var proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        var result : String? = null

        var cursorLoader : CursorLoader = CursorLoader(context, contentUri, proj, null, null, null)
        var cursor : Cursor? = cursorLoader.loadInBackground()

        if(cursor != null) {
            var index : Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            result = cursor.getString(index)
            cursor.close()
        }
        return result!!
    }

    fun getRealPathFromURI_BelowAPI11(context: Context, contentUri : Uri) : String{
        var proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)

        var cursor : Cursor? = context.contentResolver.query(contentUri, proj, null, null, null)

        var result : String? = ""

        if(cursor != null) {
            var index : Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            result = cursor.getString(index)
            cursor.close()
            return result
        }
        return result!!
    }

    fun getRealPathFromURI_API19(context: Context, uri : Uri) : String? {
        val isKitKat : Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                val docId : String = DocumentsContract.getDocumentId(uri);
                val split : List<String> = docId.split(":")
                val type : String = split[0]

                if ("primary".equals(type, true)) {
                    return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                val id : String = DocumentsContract.getDocumentId(uri);
                val contentUri : Uri = ContentUris.withAppendedId(
                    Uri.parse("content://downloads/public_downloads"), id.toLong())

                return getDataColumn(context, contentUri, null, null)!!
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                val docId : String = DocumentsContract.getDocumentId(uri)
                val split : List<String> = docId.split(":")
                val type : String = split[0]

                var contentUri : Uri? = null
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                val selection : String = "_id=?";
                val selectionArgs : Array<String> = arrayOf(split[1])

                return getDataColumn(context, contentUri!!, selection, selectionArgs)!!
            }
        }
        // MediaStore (and general)
        else if ("content".equals(uri.getScheme(), true)) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment()!!

            return getDataColumn(context, uri, null, null)!!
        }
        // File
        else if ("file".equals(uri.getScheme(), true)) {
            return uri.getPath()!!
        }

        return null;
    }


    fun getDataColumn(context: Context, uri: Uri, selection: String?, selectionArgs: Array<String>?) : String? {
        var cursor : Cursor? = null
        val column : String = "_data"
        val proj : Array<String> = arrayOf(column)

        try{
            cursor = context.contentResolver.query(uri, proj, selection, selectionArgs, null)
            if(cursor != null && cursor.moveToFirst()){
                val index : Int = cursor.getColumnIndexOrThrow(column)
                return cursor.getString(index)
            }
        } finally {
            if(cursor != null)
                cursor.close()
        }
        return null
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    fun isExternalStorageDocument(uri : Uri) : Boolean {
        return "com.android.externalstorage.documents".equals(uri.getAuthority())
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */

    fun isDownloadsDocument(uri : Uri) : Boolean {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority())
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    fun isMediaDocument(uri : Uri) : Boolean {
        return "com.android.providers.media.documents".equals(uri.getAuthority())
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    fun isGooglePhotosUri(uri : Uri) : Boolean {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority())
    }


}