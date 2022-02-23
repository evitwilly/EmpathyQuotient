# EmpathyQuotient

EmpathyQuotient - Android приложение, которое является измеряет коэффмциент эмпатии [эмпатии](https://inlnk.ru/VoRlJ)

Основные фишки приложения:

1. Красивые анимации переходов между экранами
2. Сохранение состояния приложения после выхода из него
3. Кастомные кнопки с прикольной анимацией и изящный шрифт **Roboto**
4. А также потрясающие эффекты!

Мое приложение могло заинтересовать других разработчиков и поэтому я опишу его особенности.

### С нуля написанная навигация

Навигация моего приложения построена на двух основных классах: **Navigator** и **ScreenStack** 

**Navigator** отвечает непосредственно за навигацию и передается в качестве параметра конструктора всем экранам приложения, что довольно удобно.

*Важное замечание: все экраны моего приложения являются View компонентами*

Содержит два основных метода:

1. **navigate(screenBuilder, arg, popToInclusive, onPush)** - переходит на соответствующий экран, параметры:
  - **screenBuilder** используется для создания экземпляра экрана
  - **arg** является экземляром **ScreenArg** и содержит свойства которые вам нужно передать другому экоану
  - **popToInclusive** содержит **id** экрана, до которого стэк будет очищен или -1, если не нужно очищать стэк при навигации
  - **onPush** отвечает за добавление нового экрана и удаление старого из дерева **View** компонентов 
2. **back()** - возвращается на предыдущий экран, возвращает **true** если стэк экранов не пустой

### Кастомные компоненты

В моем приложении есть несколько интересных кастомных компонентов:

1. **RippleImageButton** - кнопка с иконкой и *ripple*-эффектом
2. **ArrowScalingButton** - текстовая кнопка с анимацией появления стрелки
3. **AnimCheckBox** - анимированная галочка
4. **AnimDiagonalButton** - кнопка с диагональной анимацией
5. **FunnyBouncesView** - эффект на последнем экране (результаты теста)
6. **ShapedTextView** - текстовая метка, которая отображает текст вопроса

## Скриншоты

|   |   |   |
|---|---|---|
|![screenshot #1](https://github.com/KiberneticWorm/EmpathyQuotient/blob/master/screens/screen1.png)|![screenshot #2](https://github.com/KiberneticWorm/EmpathyQuotient/blob/master/screens/screen2.png)|![screenshot #3](https://github.com/KiberneticWorm/EmpathyQuotient/blob/master/screens/screen3.png)|
|![screenshot #4](https://github.com/KiberneticWorm/EmpathyQuotient/blob/master/screens/screen4.png)|![screenshot #5](https://github.com/KiberneticWorm/EmpathyQuotient/blob/master/screens/screen5.png)|

## Записи экрана

[![Приложение EmpathyQuotient (экран результата)](https://img.youtube.com/vi/OQh30DnKHBs/0.jpg)](https://www.youtube.com/watch?v=OQh30DnKHBs)
[![Приложение EmpathyQuotient (переход между вопросами)](https://img.youtube.com/vi/zp0oXagEAmw/0.jpg)](https://www.youtube.com/watch?v=zp0oXagEAmw)
[![Приложение EmpathyQuotient (экран вопроса)](https://img.youtube.com/vi/6Z8VioezDeU/0.jpg)](https://www.youtube.com/watch?v=6Z8VioezDeU)
[![Приложение EmpathyQuotient (переход между начальным экраном и тестом)](https://img.youtube.com/vi/bpcLbP7Oz5I/0.jpg)](https://www.youtube.com/watch?v=bpcLbP7Oz5I)
[![Приложение EmpathyQuotient (начальный экран)](https://img.youtube.com/vi/1qs_LreyW7g/0.jpg)](https://www.youtube.com/watch?v=1qs_LreyW7g)
