#ifndef FINDDLG_H
#define FINDDLG_H

#include <QDialog>//对话框
#include <QStack>
#include<QPushButton>

//#if _MSC_VER >= 1600
//#pragma execution_character_set("utf-8")
//#endif
//防止中文注释编译错误，已经在.pro中用另一方法

//QT_BEGIN_NAMESPACE
namespace Ui
{
    class Replace;
}
//QT_END_NAMESPACE
//QT4 中旧用法

class Editor;//编辑器
class Replace : public QDialog//对话框
{
    Q_OBJECT

public:
    explicit Replace(QWidget *parent = nullptr);//explicit防止隐式转换
    //QWidget 微件
    ~Replace();

    QString nowText;//当前文本框存的值
    int lastIndex;//上次查到的值
    QStack<int> stkFound; //已找到字符栈

signals:
    void sendIndex(int, int);
    void sendQString(QString);

private slots:
    bool find();
    bool next();
    bool findNext();
    bool replace();
    bool replaceAll();

private:
    Ui::Replace *ui;//FindDlg  ui设计
};

#endif // FINDDLG_H
