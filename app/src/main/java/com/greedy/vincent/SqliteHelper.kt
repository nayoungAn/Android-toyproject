package com.greedy.sqlite

import android.annotation.SuppressLint
import android.content.ClipData.Item
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.greedy.vincent.Comment

class SqliteHelper (context: Context, name: String, version: Int) : SQLiteOpenHelper(context, name, null, version) {


    /* 데이터베이스가 생성될 때 동작하는 메소드 */
    override fun onCreate(db: SQLiteDatabase?) {

        /* 데이터베이스가 생성될 때 테이블을 생성한다. */
        val create = "create table comment (" +
                "no integer primary key, " +
                "contentId text, " +
                "content text, " +
                "datetime integer " +
                ")"

        db?.execSQL(create)
    }

    /* 데이터베이스가 업그레이드 될 때 동작하는 메소드 */
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        /* 내용 없음 */
    }

    /* 1. insert */
    fun insertComment(comment: Comment) {

        /* 저장할 데이터를 ContentValues에 key value 방식으로 저장한다. */
        val values = ContentValues()
        values.put("contentId", comment.contentId)
        values.put("content", comment.content)
        values.put("datetime", comment.datetime)

        val wd = writableDatabase
        wd.insert("comment", null, values)
        wd.close()
    }

    /* 2. select */
    @SuppressLint("Range")
    fun selectComment(contentId:String): MutableList<Comment> {

        /* 데이터베이스가 존재하지 않는다면 onCreate를 호출해서 테이블을 생성한다. */
        if(readableDatabase == null) {
            onCreate(readableDatabase)
        }

        /* 조회 시에는 readableDatabase를 이용한다. */
        val rd = readableDatabase
        val select = "select * from comment where contentId = ${contentId}"
        val list = mutableListOf<Comment>()

        /* 조회 결과는 cursor 형태로 되돌아 오는데 조회 시 쿼리문과 쿼리문에 전달할 값을 인자로 전달한다. */
        var cursor = rd.rawQuery(select, null)

        while(cursor.moveToNext()) {
            val no = cursor.getLong(cursor.getColumnIndex("no"))
            val contentId = cursor.getString(cursor.getColumnIndex("contentId"))
            val content = cursor.getString(cursor.getColumnIndex("content"))
            val datetime = cursor.getLong(cursor.getColumnIndex("datetime"))

            list.add(Comment(no, contentId, content, datetime))
        }

        cursor.close()
        rd.close()

        return list
    }

    /* 3. delete */
    fun deleteMemo(comment: Comment) {
        val delete = "delete from comment where no = ${comment.no}"
        val db = writableDatabase
        db.execSQL(delete)
        db.close()
    }


}