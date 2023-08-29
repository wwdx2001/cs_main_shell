/**
 * @author qiweiwei
 */
package com.sh3h.dataprovider;

import android.content.Context;

import com.sh3h.dataprovider.greendaoEntity.Track;
import com.sh3h.dataprovider.greendaoEntity.Word;

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
