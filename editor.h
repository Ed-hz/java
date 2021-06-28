#ifndef EDITOR_H
#define EDITOR_H

#include "textarea.h"
#include "Replace.h"

#include <QMainWindow>//主界面
#include <QTextCharFormat>//文本信息

//#if _MSC_VER >= 1600
//#pragma execution_character_set("utf-8")
//#endif
//防止中文注释编译错误，已经在.pro中用另一方法

//QT_BEGIN_NAMESPACE
namespace Ui
{
    class Editor;
}
//QT_END_NAMESPACE
//QT4 中旧用法

class Editor : public QMainWindow
{
    Q_OBJECT//宏定义，使用signals和solts的条件

public:
    explicit Editor(QWidget *parent = nullptr);//explicit防止隐式转换
    ~Editor();
    //////////////////////////////////////
    /// \brief replaceIndex
    ///
    int replaceIndex;//替换文本下标
    int replaceLength;//替换文本长度
    int lastIndex;//上次查找文本下标
    int lastLength;//上次查找文本长度
    //////////////////////////////////////
    /// \brief stackUndo
    ///
    QStack<QString> stackUndo;//Undo栈
    QStack<QString> stackRedo;//Redo栈
    QString pasteBoard;//复制粘贴区

    //////////////////////////////////////
    /// \brief appendPage
    ///
    void appendPage();//加页
    void updatePage();//更新页
    void mergeformat(const QTextCharFormat &fmt);
    void resetStack();
    TextArea *textArea;//页面上的文本框
private:
    //////////////////////////////////////
    /// \brief ui
    ///
    Ui::Editor *ui;//Editor ui设计
    QTextDocument *doc;//存放dom化的html文件
    QGraphicsScene *docScene;//绘制图形页面
    QTextCharFormat start;
    //////////////////////////////////////
    /// \brief page
    ///
    QList<QGraphicsRectItem *> page;//页面长方形列表
    //QList<QGraphicsEllipseItem *> page;
    //椭圆失败了
    QString curFile;//文件名
    Replace *f;//find&replace 窗体
    struct
    {
        double width, height, margin, padding;
    }
    pageStyle;//页面数据（长、宽、空白边距、页边距）
    //////////////////////////////////////
    /// \brief isLoadFile
    ///
    bool isLoadFile=0;//是否成功load
    bool isUntitled;//是否保存过
    bool isUndo=0;//是否撤销
    bool isRedo=0;//是否重做   
    bool undoIsUsed=0;//
    //////////////////////////////////////
    /// \brief isbold
    ///
    bool isbold=0;//是否加粗
    bool isitatic=0;//是否倾斜
    bool isunderline=0;//是否下划线
    bool ishtml = 0;//是否html
public slots:   

private slots:
    //////////////////////////////////////
    /// \brief receiveIndex
    /// \param index
    /// \param length
    ///
    void receiveIndex(int index, int length);
    void receiveQString(QString replaceStr);
    void receiveSignal();
    /////////////////////////////////////////////////
    /// \brief textChanged
    ///
    void textChanged();
    bool IsExist();
    void new_();
    void open();
    bool save();
    bool saveAs();
    bool saveFile(const QString &fileName);
    bool loadFile(const QString &fileName);
    void closeEvent(QCloseEvent *event);
    //////////////////////////////////////
    /// \brief textBold
    ///
    void textBold();
    void textItalic();
    void textUnderline();
    //////////////////////////////////////
    /// \brief textColor
    ///
    void textColor();
    void textFont();
    //////////////////////////////////////
    /// \brief Left
    ///
    void Left();
    void Right();
    void Center();
    void Just();
    //////////////////////////////////////
    /// \brief undo
    ///
    void undo();
    void redo();
    //////////////////////////////////////
    /// \brief cut
    ///
    void cut();
    void copy();
    void paste();
    //////////////////////////////////////
    /// \brief zoomin
    ///
    void zoomin();
    void zoomout();
    void replace();
    //////////////////////////////////////
    /// \brief tohtml
    ///
    void tohtml();

};

#endif // EDITOR_H
