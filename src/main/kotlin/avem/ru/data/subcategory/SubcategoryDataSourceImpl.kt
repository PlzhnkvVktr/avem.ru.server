package avem.ru.data.subcategory

import avem.ru.data.model.Subcategory
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class SubcategoryDataSourceImpl(
    db: CoroutineDatabase
): SubcategoryDataSource {
    private val subcategories = db.getCollection<Subcategory>()
    override suspend fun getAllSubcategory(): List<Subcategory> =
        subcategories.find().toList()

    override suspend fun getSubcategoryByParent(parent: String): Subcategory? =
        subcategories.findOne(Subcategory::parent eq parent)


}