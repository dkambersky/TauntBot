class AsciiDrawer{
    val Canvas = mutableListOf(mutableListOf<Char>())

    fun drawChar(c:Char, x:Int, y:Int){
        while (Canvas.count()-1<x){
            Canvas.add(mutableListOf<Char>())
        }
        while (Canvas[x].count()-1<y){
            Canvas[x].add(' ')
        }
        Canvas[x][y] = c
    }
    
    fun drawTextLine(s:String, x:Int, y:Int){
        for (i in s.indices) {
            drawChar(s[i], x+i, y)
        }
    }


    fun drawText(s:String, x:Int, y:Int){
        val lines = s.lines()
        for (i in lines.indices) {
            drawTextLine(lines[i], x, y+i)
        }
    }

    fun drawBox(x: Int, y: Int, width:Int, height:Int, clearBody:Boolean = false){
        //corners
        drawChar('┌', x, y)
        drawChar('└', x, y+height-1)
        drawChar('┐', x+width-1, y)
        drawChar('┘', x+width-1, y+height-1)
        //top and bottom line
        for (i in x+1..x+width-2){
            drawChar('─', i, y)
            drawChar('─', i, y+height-1)
        }
        //middle
        for (i in y+1..y+height-2){
            drawChar('│', x, i)
            drawChar('│', x+width-1, i)
            if(clearBody){
                for(j in x+1..x+width-2){
                    drawChar(' ', j, i)
                }
            }
        }

    }

    override fun toString(): String {
        val sb = StringBuilder()
        val maxY = Canvas.maxBy { it.count() }?.count()
        for (i in 0..(maxY?:0)){
            for (l in Canvas){
                if (l.count()-1<i){
                    sb.append(' ')
                }else{
                    sb.append(l[i])
                }
            }
            sb.appendln()
        }
        return sb.toString()
    }
}