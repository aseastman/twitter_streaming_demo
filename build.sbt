

resolvers ++= Seq("mvnrepository" at "http://mvnrepository.com/artifact/",
  "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/",
  "Cloudera Repository" at "https://repository.cloudera.com/artifactory/cloudera-repos/")

val spark_version = "1.5.0"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % spark_version,
  "org.apache.spark" %% "spark-sql" % spark_version,
  "org.apache.spark" %% "spark-hive" % spark_version,
  "org.apache.spark" % "spark-streaming-twitter_2.10" % spark_version,
  "org.apache.spark" % "spark-streaming_2.10" % spark_version
)

lazy val root = (project in file(".")).
  settings(
    name := "Spark-H",
    version := "0.1"
  )