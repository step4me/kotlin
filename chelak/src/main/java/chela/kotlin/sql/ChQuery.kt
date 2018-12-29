package chela.kotlin.sql

import chela.kotlin.Ch
import chela.kotlin.validation.ChRuleSet

private val regParam =  "@(?:([^@:]+)(?::([^@:]+))?)@".toRegex()
private val regTrim =  """[\n\r]""".toRegex()
private class Item(val i:Int, val k:String, val ruleSet: ChRuleSet)
internal class ChQuery(body: String){
    private val items = mutableMapOf<String, Item>()
    internal val query = regTrim.replace(regParam.replace(body){
        val k = it.groupValues[1]
        var v = it.groupValues[2]
        items[k] = Item(items.size, k, when{
            v.isBlank() -> ChRuleSet["string"]
            !v.contains(".") -> ChRuleSet[v]
            else ->
                ChSql.rulesets[v] ?:
                Ch.vm.viewmodel(v.split(".")) as? ChRuleSet ?:
                ChRuleSet["string"]
        })
         "?"
    }, " ").trim()
    internal fun param(param:Array<out Pair<String, Any>>):Array<String>{
        val r = mutableListOf<String>()
        var cnt = 0
        param.forEach {(k, v)->
            items[k]?.let {
                val c = it.ruleSet.check(v)
                if(ChRuleSet.isOk(c)){
                    r.add(it.i, c.toString())
                    cnt++
                }else throw Exception("invalid type:$k - ${it.ruleSet} - $c")
            }
        }
        if(cnt != items.size) throw Exception("param not match:$cnt != ${items.size} - $query")
        return r.toTypedArray()
    }
}