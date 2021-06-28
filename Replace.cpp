#include "Replace.h"
#include "ui_Replace.h"

//#if _MSC_VER >= 1600
//#pragma execution_character_set("utf-8")
//#endif
//防止中文注释编译错误，已经在.pro中用另一方法

Replace::Replace(QWidget *parent) :
    QDialog(parent)
    ,ui(new Ui::Replace)
{
    ui->setupUi(this);// ui
    setWindowTitle(tr("查找 & 替换"));//标题

    connect(ui->actionFind, &QPushButton::clicked, this, &Replace::find);
    connect(ui->actionFindNext, &QPushButton::clicked, this, &Replace::findNext);
    connect(ui->actionReplace, &QPushButton::clicked, this, &Replace::replace);
    connect(ui->actionReplaceAll, &QPushButton::clicked, this, &Replace::replaceAll);

}

Replace::~Replace()
{
    delete ui;
}

bool Replace::find()
{
    QString str = nowText;//获取文本框内容
    QString Found = ui->findEdit->toPlainText();//获取查找框内容
    int index =  str.indexOf(Found);//查找到数字
    if (index == -1)
    {
        return false;
    }//没找到
    else
    {
        lastIndex = index;
        stkFound.push(index);
        return true;
    }//压入查找栈

    emit sendIndex(lastIndex, ui->findEdit->toPlainText().length());
}

bool Replace::next()
{
    QString Found = ui->findEdit->toPlainText();//获取查找框内容

    int index = nowText.indexOf(Found, lastIndex+1);//从上次地方开始查找
    if (index == -1)
    {
       return false;
    }
    else
    {
        lastIndex = index;
        stkFound.push(index);
        return true;
    }//同上
}

bool Replace::findNext()
{
    if (stkFound.isEmpty())
    {
        emit sendIndex(lastIndex, ui->findEdit->toPlainText().length());
        return find();
    }
    else
    {
        emit sendIndex(lastIndex, ui->findEdit->toPlainText().length());
        return next();
    }
}

bool Replace::replace()
{
    emit sendQString(ui->replaceEdit->toPlainText());

    return findNext();
}

bool Replace::replaceAll()
{
    while(replace()){}
    replace();
    return true;
}



