#include "editor.h"
#include <QApplication>
#include <stack>

//#if _MSC_VER >= 1600
//#pragma execution_character_set("utf-8")
//#endif
//防止中文注释编译错误，已经在.pro中用另一方法

int main(int argc, char *argv[])
{
    QApplication app(argc, argv);
    Editor e;//打开一个编辑器
    e.show();//显示编辑器

    return app.exec();
}
