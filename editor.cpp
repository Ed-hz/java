/////////////////////////////////////////////////
#include "editor.h"
#include "ui_editor.h"
#include "Replace.h"
/////////////////////////////////////////////////
#include <cstring>
#include<iostream>
/////////////////////////////////////////////////
#include <QInputDialog>
#include <QColorDialog>
#include <QFileDialog>
#include <QFontDialog>
/////////////////////////////////////////////////
#include <QTextDocument>//html，doc
#include <QMessageBox>//弹窗
/////////////////////////////////////////////////
#include <QPushButton>//按钮
/////////////////////////////////////////////////
#include <QTextStream>//读写字符
/////////////////////////////////////////////////
#include <QShortcut>//快捷键
/////////////////////////////////////////////////
#include <QTimer>//更新文本框
/////////////////////////////////////////////////
#include <QDebug>//debug
/////////////////////////////////////////////////
#include <QApplication>
#include <stack>
/////////////////////////////////////////////////
//#if _MSC_VER >= 1600
//#pragma execution_character_set("utf-8")
//#endif
//防止中文注释编译错误,已经在.pro中用另一方法

/////////////////////////////////////////////////
/// \brief Editor::Editor
/// \param parent
///
Editor::Editor(QWidget *parent)
    : QMainWindow(parent)
    , ui(new Ui::Editor)
{
      /*初始化QMainWindow*/
      ui->setupUi(this);//选择ui
      QMainWindow::setCentralWidget(ui->docView);

      /*struct pageStyle参数*/
      pageStyle.width = 750.0;
      pageStyle.height = 450.0;
      pageStyle.margin = 10.0;
      pageStyle.padding = 20.0;

      /*文本区*/
      textArea = new TextArea(this);
      textArea->setZValue(999);//堆栈数

      /*定义的QGraphicsScene*/
      docScene = new QGraphicsScene(this);
      docScene->addItem(textArea);
      ui->docView->setScene(docScene);

      /*document*/
      doc = textArea->document();//文本区的下一个doc
      QSizeF size(pageStyle.width, pageStyle.height + pageStyle.margin);
      doc->setPageSize(size);//定义二维size
      doc->setDocumentMargin(pageStyle.padding);
      appendPage();//第一页
      isUntitled = true;// 初始化文件为未保存
      curFile = tr("未命名.txt");// 初始化文件名"未命名.txt"
      setWindowTitle(curFile + tr(" - Editor"));// 初始化窗口标题为文件名
      stackUndo.push(textArea->toPlainText());

      /*默认格式*/
      /*
       * 新細明體：PMingLiU
       * 細明體：MingLiU
       * 標楷體：DFKai-SB
       * 黑体：SimHei
       * 宋体：SimSun
       * 新宋体：NSimSun
       * 仿宋：FangSong
       * 楷体：KaiTi
       * 仿宋_GB2312：FangSong_GB2312
       * 楷体_GB2312：KaiTi_GB2312
       * 微軟正黑體：Microsoft JhengHei
       * 微软雅黑体：Microsoft YaHei
       * 隶书：LiSu
       * 幼圆：YouYuan
       * 华文细黑：STHeiti Light [STXihei]
       * 华文黑体：STHeiti
       * 华文楷体：STKaiti
       * 华文宋体：STSong
       * 华文仿宋：STFangsong
       */
      QFont font;
      font.setFamily("Microsoft JhengHei");//设置文字字体
      //font.setStyle(QFont::StyleItalic);//设置文字倾斜
      font.setWeight(QFont::Light);//设置文字粗细
      //font.setLetterSpacing(QFont::PercentageSpacing,10); //设置字间距
      font.setLetterSpacing(QFont::AbsoluteSpacing,5); //设置字间距像素值
      //font.setCapitalization(QFont::Capitalize);//设置首个字母大写,全部大写AllUppercase
      font.setPixelSize(5);//设置文字像素
      font.setPointSize(9);//设置文字大小
      font.setBold(0);//设置文字为粗体
      font.setItalic(0);//设置文字为斜体
      font.setOverline(0);//设置文字上划线
      font.setUnderline(0);//设置文字下划线
      font.setStrikeOut(0);//设置文字中划线
      start.setFont(font);
      QColor color = Qt::gray;//QColorDialog::getColor(Qt::red,this);
      start.setForeground(color);

      /*Test*/
      doc->setHtml("<p>文本<b>编辑</b>器</p><p>基本目标</p><p>- 支持基本的文本编辑功能，"
                   "包括输入，回退，插入，删除，撤销，重做等</p><p>- 支持文件的保存和打开"
                   "</p><p>- 支持搜索与替换</p><p>高级目标</p><p>- 支持文字格式的设置，"
                   "包括大小、字体、颜色等</p><p>- 支持排版设置，包括对齐方式、自动分页等</p>");
      //this->resize(10,20);
      //this->setWindowState(Qt::WindowFullScreen);
      //textArea ->setFocusPolicy();
      //////////////////////////////////////

      /*statusbar*/
      ui->statusbar->showMessage(tr("新建文档!"),2000);

      /*连接槽*/
      //////////////////////////////////////
      /// \brief connect
      ///

      ///
      connect(ui->actionNew, &QAction::triggered, this, &Editor::new_);
      connect(ui->actionOpen, &QAction::triggered, this, &Editor::open);
      connect(ui->actionSave, &QAction::triggered, this, &Editor::save);
      connect(ui->actionSaveas, &QAction::triggered, this, &Editor::saveAs);
      //////////////////////////////////////
      /// \brief connect
      ///
      connect(ui->actionBold, &QAction::triggered, this, &Editor::textBold);
      connect(ui->actionItalic, &QAction::triggered, this, &Editor::textItalic);
      connect(ui->actionUnderline, &QAction::triggered, this, &Editor::textUnderline);
      //////////////////////////////////////
      /// \brief connect
      ///
      connect(ui->actionColor, &QAction::triggered, this, &Editor::textColor);
      connect(ui->actionFont, &QAction::triggered, this, &Editor::textFont);
      //////////////////////////////////////
      /// \brief connect
      ///
      connect(doc,SIGNAL(textChanged(QString)), this, SLOT(&Editor::textChanged()));
      connect(ui->actionUndo, &QAction::triggered, this, &Editor::undo);
      connect(ui->actionRedo, &QAction::triggered, this, &Editor::redo);
      //////////////////////////////////////
      /// \brief connect
      ///
      connect(ui->actionCopy, &QAction::triggered, this, &Editor::copy);
      connect(ui->actionPaste, &QAction::triggered, this, &Editor::paste);
      connect(ui->actionCut, &QAction::triggered, this, &Editor::cut);
      //////////////////////////////////////
      /// \brief connect
      ///
      connect(ui->actionLeft, &QAction::triggered, this, &Editor::Left);
      connect(ui->actionRight, &QAction::triggered, this, &Editor::Right);
      connect(ui->actionCenter, &QAction::triggered, this, &Editor::Center);
      connect(ui->actionJust, &QAction::triggered, this, &Editor::Just);

      //////////////////////////////////////
      /// \brief connect
      ///
      connect(ui->actionZoomIn, &QAction::triggered, this, &Editor::zoomin);
      connect(ui->actionZoomOut, &QAction::triggered, this, &Editor::zoomout);
      connect(ui->actionReplace, &QAction::triggered, this, &Editor::replace);
      connect(ui->actionHtml, &QAction::triggered, this, &Editor::tohtml);
      connect(textArea,SIGNAL(textChanged()),this,SLOT(receiveSignal()));

}

Editor::~Editor()
{
      delete ui;
      delete docScene;
      //delete textArea;
}

/////////////////////////////////////////////////
/// \brief Editor::appendPage
///
void Editor::appendPage()
{
     int num = page.length();//当前页数
     QGraphicsRectItem *page_next = new QGraphicsRectItem(0,//左右偏移
         num * (pageStyle.height + pageStyle.margin),//上下偏移
         pageStyle.width, pageStyle.height);//长方形长宽
     page_next ->setBrush(QBrush(Qt::white,Qt::Dense2Pattern));//背景色
     page.append(page_next);
     docScene->addItem(page_next);
}

void Editor::updatePage()
{
    if (doc->pageCount() < page.length())
    {
        while (doc->pageCount() < page.length())
        {
            docScene->removeItem(page.last());
            page.removeLast();
        }//减页
    } else
    {
        while (doc->pageCount() > page.length())
            appendPage();
    }//加页
}

/////////////////////////////////////////////////
/// \brief Editor::IsExist
/// \return
///
bool Editor::IsExist()
{
    // 如果文档被更改了
    if ((textArea ->document()->isModified())||
            ((undoIsUsed)&&(!stackUndo.isEmpty())))
    {
    // 自定义一个警告对话框
       QMessageBox box;
       box.setWindowTitle(tr("警告"));
       box.setIcon(QMessageBox::Warning);
       box.setText(curFile + tr(" 尚未保存，是否保存？"));
       QPushButton *yesBtn = box.addButton(tr("是(&Y)"),
                        QMessageBox::YesRole);
       box.addButton(tr("否(&N)"), QMessageBox::NoRole);
       QPushButton *cancelBut = box.addButton(tr("取消"),
                        QMessageBox::RejectRole);
       box.exec();
       if (box.clickedButton() == yesBtn)
            return save();
       else if (box.clickedButton() == cancelBut)
            return false;
   }
   // 如果文档没有被更改，则直接返回true
   return true;
}

void Editor::new_()
{
   Editor * f = new Editor;//打开一个编辑器
   f->show();//显示编辑器
}

void Editor::open()
{

    QString fileName = QFileDialog::getOpenFileName(this);
    if (!fileName.isEmpty())
    {
         Editor * f = new Editor;//打开一个编辑器
         f->show();//显示编辑器
         f->loadFile(fileName);
         textArea->setVisible(true);
         f->docScene ->setFocus();
    }

}

bool Editor::save()
{

   if (isUntitled)
   {
       //QCloseEvent *event;
       //event->accept();
       QMessageBox::information(this,curFile,tr("是否保存？"));
       return saveAs();
   } else
   {
       return saveFile(curFile);
   }
}

bool Editor::saveAs()
{
   QString fileName = QFileDialog::
           getSaveFileName(this, tr("另存为"),curFile);
   if(fileName.isEmpty())
   {
       return false;
   }else
   {
       return saveFile(fileName);
   }
}

bool Editor::saveFile(const QString &fileName)
{
   QFile file(fileName);

   if (!file.open(QFile::WriteOnly | QFile::Text))
   {
       // %1和%2分别对应后面arg两个参数，/n起换行的作用
       QMessageBox::warning(this, tr("Editor"),
                   tr("无法写入文件 %1：/n %2")
                  .arg(fileName).arg(file.errorString()));
       return false;
   }
   QTextStream out(&file);
   // 鼠标指针变为等待状态
   QApplication::setOverrideCursor(Qt::WaitCursor);
   out << textArea->toPlainText();
   // 鼠标指针恢复原来的状态
   QApplication::restoreOverrideCursor();
   isUntitled = false;
   // 获得文件的标准路径
   curFile = QFileInfo(fileName).canonicalFilePath();
   setWindowTitle(curFile);
   return true;
}

bool Editor::loadFile(const QString &fileName)
{
   //resetStack();
   QFile file(fileName); // 新建QFile对象
   if (!file.open(QFile::ReadOnly | QFile::Text)) {
       QMessageBox::warning(this, tr("多文档编辑器"),
                             tr("无法读取文件 %1:\n%2.")
                             .arg(fileName).arg(file.errorString()));
       return false; // 只读方式打开文件，出错则提示，并返回false
   }

   QTextStream in(&file); // 新建文本流对象
   QApplication::setOverrideCursor(Qt::WaitCursor);
   // 读取文件的全部文本内容，并添加到编辑器中
//   isLoadFile=1;
//   resetStack();
   textArea->setPlainText(in.readAll());
   resetStack();
   stackUndo.push(textArea->toPlainText());
   QApplication::restoreOverrideCursor();

   // 设置当前文件
   curFile = QFileInfo(fileName).canonicalFilePath();
   setWindowTitle(curFile + tr(" - TextEditor"));
   //   strRedo.push(ui->textEdit->toPlainText());

//      strRedo.push(ui->textEdit->toPlainText());
      //textArea->moveCursor(QTextCursor::End, QTextCursor::MoveAnchor);
   return true;
}

void Editor::closeEvent(QCloseEvent *event)
{
    if(IsExist())//saveBeforeAction())
    {
        event->accept();
    }else{
        event->ignore();
    }
}

/////////////////////////////////////////////////
/// \brief Editor::textBold
///
void Editor::textBold()
{
    QTextCharFormat fmt;
    if(!isbold)
    {
        fmt.setFontWeight( QFont::Bold );
    }else{
        fmt.setFontWeight( QFont::Normal );
    }
    isbold = !isbold;
    mergeformat( fmt );

    updatePage();

    ui->statusbar->showMessage(tr("加粗"),2000);
}

void Editor::textItalic()
{
    QTextCharFormat fmt;

    if(!isitatic)
    {
        fmt.setFontItalic( 1 );
    }else{
        fmt.setFontItalic( 0 );
    }
    isitatic = !isitatic;
    mergeformat( fmt );

    updatePage();
    ui->statusbar->showMessage(tr("倾斜"),2000);
}

void Editor::textUnderline()
{
    QTextCharFormat fmt;

    if(!isunderline)
    {
        fmt.setFontUnderline( 1 );
    }else{
        fmt.setFontUnderline( 0 );
    }
    isunderline = !isunderline;
    mergeformat( fmt );

    ui->statusbar->showMessage(tr("下划线"),2000);
}

/////////////////////////////////////////////////
/// \brief Editor::textColor
///
void Editor::textColor()
{
    QColor color =QColorDialog::getColor(Qt::red, this );
    if ( color.isValid() )
    {
         QTextCharFormat fmt;

         fmt.setForeground( color );

         mergeformat( fmt );
    }
}

void Editor::textFont()
{
    bool ok;
    QFont font = QFontDialog::getFont(&ok);
    if(ok)
    {
        QTextCharFormat fmt;

        fmt.setFont(font );

        mergeformat( fmt );
    }
    updatePage();
}

/////////////////////////////////////////////////
/// \brief Editor::cut
///
void Editor::cut()
{
    QTextCursor cursor =textArea->textCursor();
    pasteBoard=cursor.selectedText();
    cursor.removeSelectedText();
    ui->statusbar->showMessage(tr("剪切"),2000);
}

void Editor::copy()
{

    QTextCursor cursor =textArea->textCursor();
    pasteBoard=cursor.selectedText();
    ui->statusbar->showMessage(tr("复制"),2000);
}

void Editor::paste()
{
    QTextCursor cursor =textArea->textCursor();
    cursor.removeSelectedText();
    cursor.insertText(pasteBoard);
    updatePage();
    ui->statusbar->showMessage(tr("粘贴"),2000);
}

/////////////////////////////////////////////////
/// \brief Editor::resetStack
///
void Editor::resetStack()
{
    stackUndo.clear();
    stackRedo.clear();
    undoIsUsed=0;
}

void Editor::textChanged()
{
    //测试代码
    printf("------------");
    if((!isUndo)&&(!isRedo)&&(!isLoadFile))
    {
        stackUndo.push(textArea->toPlainText());
    }
    std::cout<<"---------------";
}

void Editor::undo()
{
    undoIsUsed=1;
    isUndo=1;
    if(stackUndo.size()>1)
    {
        stackRedo.push(stackUndo.pop());//重做栈压入撤销前的内容
        textArea->setPlainText(stackUndo.pop());//弹出撤销前的内容
        stackRedo.push(textArea->toPlainText());//
        QTextCursor cursor = textArea->textCursor();
        cursor.movePosition(QTextCursor::End, QTextCursor::MoveAnchor);
        //textArea->moveCursor(QTextCursor::End, QTextCursor::MoveAnchor);
    }
    isUndo=0;

    updatePage();
}

void Editor::redo()
{
    isRedo=1;
    if(stackRedo.size()>1)
    {
        stackUndo.push(stackRedo.pop());
        textArea->setPlainText(stackRedo.pop());
        stackUndo.push(textArea->toPlainText());
        QTextCursor cursor = textArea->textCursor();
        cursor.movePosition(QTextCursor::End, QTextCursor::MoveAnchor);
        //textArea->moveCursor(QTextCursor::End, QTextCursor::MoveAnchor);
    }
    isRedo=0;

    updatePage();
}

/////////////////////////////////////////////////
/// \brief Editor::mergeformat
/// \param fmt
///
void Editor::mergeformat(const QTextCharFormat &fmt)
{
    QTextCursor cursor = textArea->textCursor();
    if ( !cursor.hasSelection() )
           cursor.select( QTextCursor::Document );
    cursor.mergeCharFormat( fmt );
    //textArea->mergeCurrentCharFormat( fmt );

}

void Editor::Left()
{
    QTextBlockFormat format;
    format.setAlignment(Qt::AlignLeft);

    QTextCursor cursor = textArea->textCursor();
    cursor.select(QTextCursor::Document);
    cursor.mergeBlockFormat(format);
    /*cursor.clearSelection();
    textArea->setTextCursor(cursor);*/
    if(ui->actionLeft->isChecked())
    {
            ui->actionRight->setChecked(false);
            ui->actionCenter->setChecked(false);
    }
     else ui->actionLeft->setChecked(true);
    ui->statusbar->showMessage(tr("左对齐"),2000);
}

void Editor::Right()
{
    QTextBlockFormat format;
    format.setAlignment(Qt::AlignRight);

    QTextCursor cursor = textArea->textCursor();
    cursor.select(QTextCursor::Document);
    cursor.mergeBlockFormat(format);
    /*cursor.clearSelection();
    textArea->setTextCursor(cursor);*/

    doc->defaultTextOption().setAlignment(Qt::AlignRight );
    if(ui->actionRight->isChecked())
    {
            ui->actionLeft->setChecked(false);
            ui->actionCenter->setChecked(false);
            ui->actionJust->setChecked(false);
    }
    else ui->actionRight->setChecked(true);
    ui->statusbar->showMessage(tr("右对齐"),2000);
}

void Editor::Center()
{
    QTextBlockFormat format;
    format.setAlignment(Qt::AlignCenter);

    QTextCursor cursor = textArea->textCursor();
    cursor.select(QTextCursor::Document);
    cursor.mergeBlockFormat(format);
    /*cursor.clearSelection();
    textArea->setTextCursor(cursor);*/
    if(ui->actionCenter->isChecked())
    {
            ui->actionJust->setChecked(false);
            ui->actionRight->setChecked(false);
            ui->actionLeft->setChecked(false);
    }
    else ui->actionCenter->setChecked(true);

    ui->statusbar->showMessage(tr("中间对齐"),2000);
}

void Editor::Just()
{
    QTextBlockFormat format;
    format.setAlignment(Qt::AlignJustify);

    QTextCursor cursor = textArea->textCursor();
    cursor.select(QTextCursor::Document);
    cursor.mergeBlockFormat(format);
    /*cursor.clearSelection();
    textArea->setTextCursor(cursor);*/
    if(ui->actionJust->isChecked())
    {
            ui->actionCenter->setChecked(false);
            ui->actionRight->setChecked(false);
            ui->actionLeft->setChecked(false);
    }
    else ui->actionJust->setChecked(true);


    ui->statusbar->showMessage(tr("两端对齐"),2000);
}

/////////////////////////////////////////////////
/// \brief Editor::zoomin
///
void Editor::zoomin()
{
    ui->docView->scale(1.25, 1.25);
}

void Editor::zoomout()
{
    ui->docView->scale(0.8, 0.8);
    //ui->docView->translate(12, 8);
    //ui->docView->shear(0.8, 0.8);
    //ui->docView->rotate(80);
    //ui->docView->transform();


    ui->statusbar->showMessage("",2000);
}

/////////////////////////////////////////////////
/// \brief Editor::receiveIndex
/// \param index
/// \param length
///
void Editor::receiveIndex(int index, int length)
{
    Q_UNUSED(index);
    Q_UNUSED(length);

    replaceIndex = index;
    replaceLength = length;

    //先把之前的格式清除
    QTextCursor cursor1 = textArea->textCursor(); //获取当前光标
    cursor1.setPosition(lastIndex);                       //定位到下标index的位置
    cursor1.setPosition(lastIndex+lastLength, QTextCursor::KeepAnchor);   //文本选择范围[index,index + length]
    //选中完成
    QTextCharFormat fmt;//定义突出显示
    fmt.setBackground(Qt::white);//定义显示的格式
    cursor1.mergeCharFormat(fmt);//显示

    //再显示当前的格式
    QTextCursor cursor2 = textArea->textCursor(); //获取当前光标
    cursor2.setPosition(index);                       //定位到下标index的位置
    cursor2.setPosition(index+length, QTextCursor::KeepAnchor);   //文本选择范围[index,index + length]
    //选中完成
    //QTextCharFormat fmt;//定义突出显示
    fmt.setForeground(Qt::red);//定义显示的格式
    cursor2.mergeCharFormat(fmt);//显示

    lastIndex = index;
    lastLength = length;

}

void Editor::receiveSignal()
{
    printf("------------");
}

void Editor::receiveQString(QString replaceStr)
{
    QString str = (textArea->toPlainText()).
            replace(replaceIndex, replaceLength, replaceStr);
    textArea->setPlainText(str);

}

void Editor::replace()
{
    f = new Replace();
    f->nowText = textArea->toPlainText();
    //连接
    connect(f,SIGNAL(sendIndex(int, int)),this,SLOT(receiveIndex(int, int)));
    connect(f,SIGNAL(sendQString(QString)),this,SLOT(receiveQString(QString)));
    //显示子对话框
    f->show();
    ui->statusbar->showMessage(tr("查找&交换"),2000);
}

/////////////////////////////////////////////////
/// \brief Editor::tohtml
///
void Editor::tohtml()
{
    if(!ishtml)
    {
        QString str=doc->toHtml();
        textArea->setPlainText(str);
        QTextCursor cursor = textArea->textCursor();
        if ( !cursor.hasSelection() )
               cursor.select( QTextCursor::Document );
        cursor.mergeCharFormat(start);
        updatePage();

        ui->actionReplace->setEnabled(false);
        ui->actionBold->setEnabled(false);
        ui->actionItalic->setEnabled(false);
        ui->actionUnderline->setEnabled(false);
        ui->actionColor->setEnabled(false);
        ui->actionFont->setEnabled(false);
        ui->statusbar->showMessage(tr("显示html"),2000);

    }else
    {
        doc->setHtml(textArea->toPlainText());
        updatePage();

        ui->actionReplace->setEnabled(true);
        ui->actionBold->setEnabled(true);
        ui->actionItalic->setEnabled(true);
        ui->actionUnderline->setEnabled(true);
        ui->actionColor->setEnabled(true);
        ui->actionFont->setEnabled(true);
        ui->statusbar->showMessage(tr("显示文本"),2000);
        //canEncode；
    }
    ishtml=!ishtml;

}
