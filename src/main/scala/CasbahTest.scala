/**
 * Created by Luffy on 2014/5/30.
 */
import com.mongodb.casbah.Imports._
object CasbahTest {

  def main(args:Array[String]) = {
    // get collection directly from a MongoDB connection
    val SERVER = "172.19.17.202"
    val collection = MongoConnection(SERVER)("debugo")("info")

    //adding documents
    val doc1 = MongoDBObject("name" -> "Charles", "city" -> "Beijing", "email" -> "charles@debugo.com", "salary" -> 1000)
    collection += doc1

    val builder = MongoDBObject.newBuilder
    builder += "name" -> "Leo"
    builder += "city" -> "Beijing"
    builder += "address" -> "Chaoyang district"
    builder += "email" -> "leo@debugo.com"
    builder += "salary" -> 1500
    collection += builder.result

    val doc3 = MongoDBObject("name" -> "Eric", "email" -> "eric@debugo.com",
      "address" -> MongoDBObject("street" -> "Guangqu road", "district" -> "Chaoyang", "city" -> "Beijing"),
      "salary" -> 2000
    )
    collection += doc3

    //create an index
    collection.ensureIndex("name")

    //query info
    //distinct name
    println("distinct docs:")
    collection.distinct("name") foreach (println _)

    // the first document of all docs
    println("all docs:")
    collection.findOne foreach (println _)

    //query with DSL
    val condition = ("salary" $gte 1000 $lt 2000) ++ ("email" $exists true)
    println("query with DSL: " + condition)
    collection.find(condition) foreach (println _)

    //drop the collection
    collection.dropCollection
  }
}
