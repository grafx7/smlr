package ru.fnnetrolle.smlr.model.repositories

import com.github.springtestdbunit.annotation.DatabaseOperation
import com.github.springtestdbunit.annotation.DatabaseSetup
import com.github.springtestdbunit.annotation.DatabaseTearDown
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.hasSize
import org.junit.Assert
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.TestPropertySource
import ru.fnnetrolle.smlr.model.AbstractRepositoryTest
import ru.fnnetrolle.smlr.model.Link
import java.util.*

@TestPropertySource(locations = arrayOf("classpath:repositories-test.properties"))
@DatabaseSetup(LinkRepositoryTest.DATASET)
@DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = [LinkRepositoryTest.DATASET])
open class LinkRepositoryTest: AbstractRepositoryTest() {

    @Autowired
    lateinit var repository: LinkRepository

    @Test
    fun findOneExisting() {
        val got: Optional<Link> = repository.findOne(LINK_1_ID)
        Assert.assertThat(got.isPresent, equalTo(true))
        val link = got.get()
        Assert.assertThat(link, equalTo(Link(LINK_1_TEXT, LINK_1_ID)))

    }

    @Test
    fun findOneNotExisting() {
        val got: Optional<Link> = repository.findOne(LINK_NOT_FOUND)
        Assert.assertThat(got.isPresent, equalTo(false))
    }

    @Test
    fun saveNew() {
        val toBeSave: Link = Link(LINK_TBS_TEXT)
        val got: Link = repository.save(toBeSave)
        val list: List<Link> = repository.findAll()

        Assert.assertThat(list, hasSize(4))
        Assert.assertThat(got.text, equalTo(LINK_TBS_TEXT))
    }

    companion object {
        const val DATASET = "classpath:datasets/link-table.xml"
        private val LINK_NOT_FOUND: Long = 1L
        private val LINK_1_ID: Long = 100500L
        private val LINK_TBS_TEXT: String = "http://www.ru"
        private val LINK_1_TEXT: String = "http://www.eveonline.com"
    }
}