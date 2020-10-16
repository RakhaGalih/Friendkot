package com.example.friendkot

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MyFriendDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun tambahTeman(friend: MyFriend)

    @Query("SELECT * FROM MyFriend")
    fun ambilSemuaTeman():LiveData<List<MyFriend>>
}

@Database(entities = [MyFriend::class], version = 1)
abstract class AppDatabase:RoomDatabase(){
    abstract fun myFriendDao():MyFriendDao
    companion object{
        var INSTANCE:AppDatabase? = null
        fun getAppDataBase(context: Context):AppDatabase?{
            if(INSTANCE == null){
                synchronized(AppDatabase::class){
                    INSTANCE =
                        Room.databaseBuilder(context.applicationContext,
                            AppDatabase::class.java, "MyFriendAppDB").build()
                }
            }
            return INSTANCE
        }
        fun destroyDataBase(){
            INSTANCE = null
        }
    }
}