package cn.luckierlove.windlist.handler.meta

import cn.luckierlove.windlist.constant.AutoFillConstant
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler
import org.apache.coyote.http11.Constants.a
import org.apache.ibatis.reflection.MetaObject
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.Date

@Component
class CommonFieldMetaObjectHandler: MetaObjectHandler {
    private val log = LoggerFactory.getLogger(CommonFieldMetaObjectHandler::class.java)
    override fun insertFill(metaObject: MetaObject?) {
        log.info("Start fill common fields...")
        this.strictInsertFill(metaObject, AutoFillConstant.CREATE_AT, Date::class.javaObjectType, Date.from(Instant.now()))
        this.strictInsertFill(metaObject, AutoFillConstant.UPDATE_AT, Date::class.javaObjectType, Date.from(Instant.now()))
    }

    override fun updateFill(metaObject: MetaObject?) {
        this.strictInsertFill(metaObject, AutoFillConstant.UPDATE_AT, Date::class.javaObjectType, Date.from(Instant.now()))
    }
}