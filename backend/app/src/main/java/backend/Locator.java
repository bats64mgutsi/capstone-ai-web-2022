package backend;

import java.util.LinkedList;
import java.util.List;

/**
 * Locator is a class used to implement the Service Locator pattern for the bot.
 *
 * <p>The idea is that Agents, Services, and all the other classes needed during runtime are
 * singletons that can be accessed with the Locator.
 *
 * <p>During app initialisation, all these objects are created and added to the Locator with
 * `registerSingleton`. During runtime all these objects can be accessed by calling `get` on the
 * locator instance.
 */
public abstract class Locator {
    /** Returns the first instance of T it finds in the list of registered dependencies. */
    public abstract Object get(Class<?> of);

    /** Registers a new dependency. */
    public abstract void registerSingleton(Object instance);

    public abstract void reset();

    public static final Locator instance = new LocatorImpl();

    private static class LocatorImpl extends Locator {
        final List<Object> dependencies = new LinkedList<>();

        @Override
        public void registerSingleton(Object instance) {
            dependencies.add(instance);
        }

        @Override
        public Object get(Class<?> of) {
            for (Object e : dependencies) {
                if (of.isInstance(e)) return e;
            }

            return null;
        }

        @Override
        public void reset() {
            dependencies.clear();
        }
    }
}