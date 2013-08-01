import com.mongodb.casbah.Imports._
import com.mongodb.casbah.gridfs.Imports._
import com.github.nscala_time.time.Imports._
import com.mongodb.casbah.commons.conversions.scala._

object CasbahTest {

  def test {

    // Insert a test filename
    val db = MongoClient()("casbah_gridfs_test")
    db.dropDatabase()

    val gridfs = JodaGridFS(db)

    val id = gridfs("hello world".getBytes()) {
      fh =>
        fh.put("uploadDate", new DateTime())
        fh.filename = "hello_world.txt"
        fh.contentType = "text/plain"
    }


    // Retrieve without JodaTimeConversions
    println("Test 1) Initial test")
    DeregisterJodaTimeConversionHelpers()
    test_file(gridfs)

    // Retrieve with JodaTimeConversions
    println("Test 2) Registering JodaTime Conversion Helpers")
    RegisterJodaTimeConversionHelpers()
    test_file(gridfs)

    // Now with a DAO in play
    val coll = db("test_salat")

    import com.novus.salat._
    import com.novus.salat.global._
    import com.novus.salat.annotations._
    import com.novus.salat.dao._

    case class Alpha(@Key("_id") id: Int, x: String)
    object AlphaDAO extends SalatDAO[Alpha, Int](coll)

    println("Test 3) With Salat - no helpers")
    DeregisterJodaTimeConversionHelpers()
    test_file(gridfs)

    println("Test 4) With Salat - with helpers")
    RegisterJodaTimeConversionHelpers()
    test_file(gridfs)

  }

  def test_file(gridfs: JodaGridFS) {
    gridfs.findOne("hello_world.txt") foreach {
    file =>
      assert ( file.filename.get == "hello_world.txt" )
      assert ( file.contentType.get == "text/plain" )
      assert ( file.uploadDate.isInstanceOf[DateTime] )
    }
  }
}