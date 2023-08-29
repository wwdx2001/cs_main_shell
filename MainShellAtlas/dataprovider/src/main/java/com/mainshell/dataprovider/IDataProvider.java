/**
 * @author qiweiwei
 */
package com.mainshell.dataprovider;

import android.content.Context;

import com.mainshell.dataprovider.greendaoEntity.Track;
import com.mainshell.dataprovider.greendaoEntity.Word;

import java.util.List;


/**
 * IMeterReadingDataProvider
 */
public interface IDataProvider {
    boolean init(String path, Context context);

    void destroy();

    void clearAllTables();

    void insertTrack(Track track);

    void clearTrack(int userId, int reservedCount);

    List<Word> getWords();

    void insertWordList(List<Word> wordList);
}
