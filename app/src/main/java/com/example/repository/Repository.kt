package com.example.repository

import com.example.model.Plant
import com.example.other.Constants.COLLECTIONS
import com.example.other.Constants.DATABASE
import com.example.other.Constants.MONGODB_SERVICE
import io.realm.mongodb.App
import io.realm.mongodb.AppConfiguration
import io.realm.mongodb.RealmResultTask
import io.realm.mongodb.mongo.MongoCollection
import io.realm.mongodb.mongo.options.InsertManyResult
import io.realm.mongodb.mongo.result.InsertOneResult
import org.bson.codecs.configuration.CodecRegistries
import org.bson.codecs.pojo.PojoCodecProvider
import javax.inject.Inject

class Repository @Inject constructor(
    app: App
) {
    private var collection: MongoCollection<Plant>

    init {
        val user = app.currentUser()
        val client = user!!.getMongoClient(MONGODB_SERVICE)
        val mongoDatabase = client.getDatabase(DATABASE)

        val pojoCodecRegistry = CodecRegistries.fromRegistries(
            AppConfiguration.DEFAULT_BSON_CODEC_REGISTRY,
            CodecRegistries.fromProviders(
                PojoCodecProvider.builder().automatic(true).build()
            )
        )
        collection = mongoDatabase.getCollection(
            COLLECTIONS,
            Plant::class.java
        ).withCodecRegistry(pojoCodecRegistry)
    }

    fun insertMany(plantList: List<Plant>): RealmResultTask<InsertManyResult> =
        collection.insertMany(plantList.toMutableList())

    fun insertOne(plant: Plant): RealmResultTask<InsertOneResult> = collection.insertOne(plant)
}