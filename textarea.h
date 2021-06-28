#ifndef TEXTAREA_H
#define TEXTAREA_H

#include <QGraphicsTextItem>//画图
#include <QInputMethodEvent>//输入事件

//#if _MSC_VER >= 1600
//#pragma execution_character_set("utf-8")
//#endif
//防止中文注释编译错误，已经在.pro中用另一方法

namespace Ui
{
    class TextArea;
}


class Editor;//编辑器
class TextArea : public QGraphicsTextItem
{
    //Q_OBJECT
public:
    TextArea(Editor *editor);
    void paint(QPainter *painter,
               const QStyleOptionGraphicsItem *option,
               QWidget *widget);
    //重载paint，QPainter画笔，QStyleOptionGraphicsItem格式，QWidget微件

protected:
    void keyPressEvent(QKeyEvent *event);
    //重载按下key
    void keyReleaseEvent(QKeyEvent *event);
    //重载放开key
    void inputMethodEvent(QInputMethodEvent *event);
    //重载输入
    void sendSignal(const QString &insertText);
    //光标输入，自动分页
    void handleInput(const QString &insert);

private:
    Editor *parentEditor;//父编辑器
    bool ctrlKey = false;;//是否按下ctrl
    QString m_store_str;//用于保存

};

#endif // TEXTAREA_H
