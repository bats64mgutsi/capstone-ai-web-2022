import { App } from "vue";
import Toast, { useToast as innerUseToast, ToastInterface } from "vue-toastification";
import "vue-toastification/dist/index.css";

/**
 * Retrieves the toast manager to use
 * for displaying toast messages.
 * This function should not be called during
 * an async operation or outside the component's
 * context.
 */
export function useToast(): ToastManager {
  // we wrap the inner toast in order to decouple
  // the app from the toast library since we might
  // likely change the implementation
  const innerToast = innerUseToast();
  return new ToastWrapper(innerToast);
}

/**
 * Registers the toast manager with the specified
 * Vue app instance, which makes it possible
 * to show toast messages using `useToast()`
 * @param app
 */
export function registerToastManager(app: App) {
  app.use(Toast, {});
}

class ToastWrapper implements ToastManager {
  constructor(private innerToast: ToastInterface) {

  }
  message(message: string, type?: MessageType | undefined): void {
    switch (type) {
      case 'error':
        this.innerToast.error(message);
        return;
      case 'success':
        this.innerToast.success(message);
        return;
      case 'warning':
        this.innerToast.warning(message);
        return;
      default:
        this.innerToast.info(message);
    }
  }

  success(message: string): void {
    this.message(message, 'success');
  }

  warn(message: string): void {
    this.message(message, 'warning');
  }

  error(error: Error|string): void {
    this.message(error instanceof Error ? error.message : error, 'error');
  }
}

export type MessageType = 'success' | 'error' | 'warning' | 'info';

export interface ToastManager {
  /**
   * Displays a toast message
   * @param message message to display
   * @param type type of message to display
   */
  message(message: string, type?: MessageType): void;
  /**
   * Displays a success toast message
   * @param message
   */
  success(message: string): void;
  /**
   * Displays a warning toast message
   * @param message
   */
  warn(message: string): void;
  /**
   * Displays an error toast message
   * @param message 
   */
  error(message: Error|string): void;
}
