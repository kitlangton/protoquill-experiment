package zio.quill

import io.getquill.context.ZioJdbc.DataSourceLayer
import io.getquill._
import zio._

import java.sql.SQLException
import javax.sql.DataSource

object QuillContext extends MagicContext {}
import QuillContext._

final case class Account(email: String)

final case class UserService(ctx: QuillContext.InnerContext) {
  def allUsers: ZIO[Any, SQLException, List[Account]] =
    ctx.run(query[Account])
}

object UserService {
  val live: URLayer[Has[DataSource], Has[UserService]] =
    QuillContext.layer >>> (UserService.apply _).toLayer
}

object Main extends App {

  def run(args: List[String]) =
    ZIO
      .serviceWith[UserService](_.allUsers)
      .debug
      .provideLayer(
        DataSourceLayer.fromPrefix("postgresDB") >>>
          UserService.live
      )
      .exitCode

}
