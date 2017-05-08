//
// Created by Administrator on 2017/4/27.
//

typedef struct Light Light;
struct Light{
int state;
void(*turnOn)(Light*);//turnOn 指向turnOn函数,Light在指回来
void(*turnOff)(Light*);
 static void turnOn(Light*cobj){
    cobj->state = 1;
    printf("ON");
 }
  static void turnOn(Light*cobj){
     cobj->state = 0;
     printf("OFF");
  }
  struct Light *LightNew(){//构造式
    struct Light *t;
    t = (Light *)malloc(sizeof(Light));
    t->turnOn = turnOn;
    t->turnOff = turnOff;

  }

}