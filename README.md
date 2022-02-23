# EmpathyQuotient

EmpathyQuotient - Android приложение, тест по измерению эмпатии [эмпатии](https://inlnk.ru/VoRlJ)

Основные фишки приложения:

1. Красивые анимации переходов между экранами (view-анимации)
2. Сохранение состояния приложения после выхода из него (shared prefs)
3. Кастомные кнопки с прикольной анимацией и изящный шрифт **Roboto** (animator's)
4. А также потрясающие эффекты!

Возможно мое приложение заинтересует других разрабов, хотя это маловероятно, потому что я не использовал
современных подходов в разработке и никакой речи о Clean Architecture идти не может.

Я люблю экспериментировать и поэтому я решил обойтись даже без Fragment'ов, а сделать все на собственной навигации с ипспользованием вьюшек.

Недавно я хотел порефакторить мою навигацию, но решил этого не делать потому что к сожалению там все страшно :)))

Помимо отказа от Fragment'ов, я еще отказался от разметки экранов в XML, а сделал весь UI кодом.

К счастью в приложении не было операций, которые нужно выполнять в фоне, а то бы дорогие мне Thread'ы пришлось создавать :)))

### Кастомные компоненты

В моем приложении есть несколько интересных кастомных компонентов:

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
