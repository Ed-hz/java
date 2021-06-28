#include "textarea.h"
#include "editor.h"
#include <QStyleOptionGraphicsItem>
#include <QTextCursor>
#include <QTextBlock>

#include <iostream>

//#if _MSC_VER >= 1600
//#pragma execution_character_set("utf-8")
//#endif
//防止中文注释编译错误，已经在.pro中用另一方法

TextArea::TextArea(Editor *editor):parentEditor(editor)
{
    this->setTextInteractionFlags(Qt::TextEditorInteraction );
}

void TextArea::paint(QPainter *painter,
                     const QStyleOptionGraphicsItem *option,
                     QWidget *widget)
{
    this->setFocus(Qt::ActiveWindowFocusReason);
    QTextCursor cur = textCursor();//光标
    Q_UNUSED(option);//option没用到

    QStyleOptionGraphicsItem op;
    op.initFrom(widget);
    op.state = QStyle::State_None;//去除文本框虚线
    return QGraphicsTextItem::paint(painter, &op, widget);

}

void TextArea::keyPressEvent(QKeyEvent *event)
{
    QTextCursor cur = textCursor();//光标

    switch (event->key())//按下的key
    {
        case Qt::Key_Control:
            ctrlKey = true;
            break;
        // 按下ctrl
        case Qt::Key_Left:
            cur.setPosition(0);
                        //movePosition(QTextCursor::Left);
            break;
        // 按下←箭头
        case Qt::Key_Right:
            textCursor().movePosition(QTextCursor::Right);
            break;
        // 按下→箭头
        case Qt::Key_Up:
            textCursor().movePosition(QTextCursor::Up);
        break;
        case Qt::Key_Z:
        break;
    }

    QString text = event->text();
    if (text.length())
    {
        handleInput(text);
    }

}

void TextArea::keyReleaseEvent(QKeyEvent *event)
{
    int keyCode = event->key();//按下的key
    if (keyCode == Qt::Key_Control) ctrlKey = false;// 放开ctrl
}

void TextArea::inputMethodEvent( QInputMethodEvent *event)
{
    QString text = event->commitString();//输入中文
    if (text.length())//有内容时
    {
        handleInput(text);
    }
}

void TextArea::handleInput(const QString &insertText)
{
      QTextCursor cur = textCursor();//光标
      if (cur.isNull()) return;//光标为null
      QTextBlock block = cur.block();//只读block
      if (!block.isValid()) return;//block不为Valid

      if (cur.selectionStart() < block.position())
      {
          while (block.position() > cur.selectionStart())
              block= block.previous();
      }
      int select = cur.selectionStart() - block.position();
      std::cout << select << std::endl;
      //输出当前输出的位置

      //emit sendSignal(insertText);

      if (ctrlKey)
      {} else
      {
          cur.removeSelectedText();
          if (insertText == '\b')
          {
              if (cur.selectionStart() == cur.selectionEnd())
              {
                  cur.deletePreviousChar();
              }
          } else
          {
              std::cout << insertText.toStdString() << std::endl;
              cur.insertText(insertText);
          }
          parentEditor->updatePage();
      }

}

/*


    void mouseDoubleClickEvent(QGraphicsSceneMouseEvent *event)override{
        if(event->button() == Qt::LeftButton){//左键双击进入可编辑状态并打开焦点
            setTextInteractionFlags(Qt::TextEditorInteraction);
            setFocus();
            QGraphicsTextItem::mouseDoubleClickEvent(event);
        }
    }

    void mousePressEvent(QGraphicsSceneMouseEvent *event)override{
        if(event->button()==Qt::LeftButton){
            //左键点击是可编辑状态的话响应单击事件
            if(textInteractionFlags() != Qt::NoTextInteraction){
                QGraphicsTextItem::mousePressEvent(event);
            }//是不可编辑状态的话，也就是未双击进入编辑状态时不响应，一遍产生双击事件
        }else{//其他按键正常流程
            QGraphicsTextItem::mousePressEvent(event);
        }
    }

    void keyPressEvent(QKeyEvent *event) override{
        if(event->key() == Qt::Key_Return||event->key() == Qt::Key_Enter)
        {
        //点击回车失去焦点，编辑完成，后续给FocusOutEvent处理
            clearFocus();
        }else{
            QGraphicsTextItem::keyPressEvent(event);
        }
    }
*/
