package zio.quill

import io.getquill.context.ZioJdbc.DataSourceLayer
import zio.{ZIO, _}
import java.sql.SQLException
import javax.sql.DataSource
import io.getquill.SnakeCase
import io.getquill.PostgresZioJdbcContext
import io.getquill.Quoted
import io.getquill.Query

abstract class MagicContext extends PostgresZioJdbcContext(SnakeCase) { self =>

  case class InnerContext(dataSource: DataSource) {
    inline def run[T](inline quoted: Quoted[Query[T]]): ZIO[Any, SQLException, List[T]] =
      InternalApi.runQueryDefault(quoted).provide(Has(dataSource))
  }

  inline def layer: ZLayer[Has[DataSource], Nothing, Has[InnerContext]] =
    (InnerContext.apply).toLayer
}
