package de.cassisi.hearth.ui.utils;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.ViewTuple;
import org.springframework.context.ApplicationContext;

public final class FastFXMLLoader {

    public static <View extends FxmlView<Model>, Model extends ViewModel> ViewTuple<View, Model>  load(Class<View> view, Class<Model> model, ApplicationContext context) {
        return FluentViewLoader.fxmlView(view)
            //    .codeBehind(context.getBean(view))
            //    .viewModel(context.getBean(model))
                .load();
    }

}
