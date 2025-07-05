package com.example.pgcnexus;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class ChatbotActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText chatInput;
    private Button sendButton;
    private ChatAdapter chatAdapter;
    private ArrayList<ChatMessage> chatMessages;

    // Handler for delayed bot responses
    private Handler handler = new Handler();

    // Pre-defined responses for common queries
    private Map<String, String[]> responseDatabase = new HashMap<>();

    // PGC Nexus specific information
    private Map<String, String> pgcInfo = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);

        // Initialize views
        recyclerView = findViewById(R.id.recyclerView);
        chatInput = findViewById(R.id.chatInput);  // Corrected ID
        sendButton = findViewById(R.id.sendButton);  // Corrected ID

        // Initialize chat messages list and adapter
        chatMessages = new ArrayList<>();
        chatAdapter = new ChatAdapter(chatMessages);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(chatAdapter);

        // Initialize response database
        initializeResponses();

        // Welcome message
        handler.postDelayed(() -> {
            addBotMessage("Hello! I'm the PGC Nexus Assistant. How can I help you today?");
        }, 500);

        // Handle Send button click
        sendButton.setOnClickListener(v -> {
            String message = chatInput.getText().toString().trim();
            if (!message.isEmpty()) {
                sendMessage(message);
            }
        });
    }

    private void initializeResponses() {
        // Campus information
        pgcInfo.put("location", "The Punjab Group of Colleges has campuses across multiple cities in Pakistan. But the BS Campus is Punjab Group Of Colleges Campus 1 , 31-C Canal Rd, Block C Muslim Town, Lahore, 54000 ");
        pgcInfo.put("programs", "PGC offers programs in Sciences, Commerce, Engineering, Medicine, Arts and more.");
        pgcInfo.put("admission", "Admissions for the new semester are now open. You can apply online through our portal or visit any campus.");
        pgcInfo.put("fees", "Fee structure varies by program. Please check the specific program page or contact the admissions office.");
        pgcInfo.put("contact", "You can contact us at info@pgc.edu.pk or call our helpline at 0800-12345.");
        pgcInfo.put("facilities", "Our campuses are equipped with state-of-the-art laboratories, well-stocked libraries, modern sports facilities, and vibrant cafeterias, providing students with a well-rounded and engaging learning environment.");
        pgcInfo.put("faculty", "PGC boasts a team of highly qualified and experienced faculty members who are experts in their respective fields, dedicated to delivering excellence in education and fostering student success");

        // Common queries and their responses
        responseDatabase.put("hello", new String[]{"Hi there!", "Hello! How can I help you?", "Greetings from PGC Nexus!"});
        responseDatabase.put("hi", new String[]{"Hello!", "Hi there! How can I assist you today?", "Hello from PGC Nexus Assistant!"});
        responseDatabase.put("bye", new String[]{"Goodbye! Have a great day!", "Bye! Feel free to chat again if you need help.", "Take care! Bye!"});
        responseDatabase.put("thanks", new String[]{"You're welcome!", "Happy to help!", "Anytime!"});
    }

    private void sendMessage(String message) {
        // Add user's message to the list
        chatMessages.add(new ChatMessage(message, true)); // true for user
        chatAdapter.notifyItemInserted(chatMessages.size() - 1);

        // Clear input
        chatInput.setText("");

        // Scroll to the last message
        recyclerView.scrollToPosition(chatMessages.size() - 1);

        // Process message and generate response with a small delay to feel natural
        handler.postDelayed(() -> processBotResponse(message), 800);
    }

    private void addBotMessage(String message) {
        chatMessages.add(new ChatMessage(message, false)); // false for bot
        chatAdapter.notifyItemInserted(chatMessages.size() - 1);
        recyclerView.scrollToPosition(chatMessages.size() - 1);
    }

    private void processBotResponse(String userMessage) {
        String lowercaseMessage = userMessage.toLowerCase(Locale.getDefault());
        String botReply;

        // Show typing indicator (optional)
        // addBotMessage("typing...");

        // Check for greetings first
        if (containsAny(lowercaseMessage, "hello", "hi", "hey", "greetings")) {
            botReply = getRandomResponse("hello");
        }
        // Check for thank you messages
        else if (containsAny(lowercaseMessage, "thanks", "thank you", "thx")) {
            botReply = getRandomResponse("thanks");
        }
        // Check for goodbyes
        else if (containsAny(lowercaseMessage, "bye", "goodbye", "see you")) {
            botReply = getRandomResponse("bye");
        }
        // Check for specific PGC information queries
        else if (containsAny(lowercaseMessage, "location", "where", "campus", "address")) {
            botReply = pgcInfo.get("location");
        }
        else if (containsAny(lowercaseMessage, "program", "course", "degree", "study")) {
            botReply = pgcInfo.get("programs");
        }
        else if (containsAny(lowercaseMessage, "admission", "apply", "enroll", "join")) {
            botReply = pgcInfo.get("admission");
        }
        else if (containsAny(lowercaseMessage, "fee", "cost", "tuition", "payment")) {
            botReply = pgcInfo.get("fees");
        }
        else if (containsAny(lowercaseMessage, "contact", "email", "phone", "call")) {
            botReply = pgcInfo.get("contact");
        }
        else if (containsAny(lowercaseMessage, "facilities", "services", "amenities")) {
            botReply = pgcInfo.get("facilities");
        }
        else if (containsAny(lowercaseMessage, "faculty", "professor", "teacher", "staff")) {
            botReply = pgcInfo.get("faculty");
        }
        // Default response if no specific pattern is matched
        else {
            botReply = "I'm not sure about that. Would you like to know about our programs, admission process, or campus facilities?";
        }

        // Remove typing indicator if implemented
        // chatMessages.remove(chatMessages.size() - 1);
        // chatAdapter.notifyItemRemoved(chatMessages.size());

        // Add bot's response
        addBotMessage(botReply);
    }

    private boolean containsAny(String source, String... targets) {
        for (String target : targets) {
            if (source.contains(target)) {
                return true;
            }
        }
        return false;
    }

    private String getRandomResponse(String key) {
        String[] responses = responseDatabase.get(key);
        if (responses != null && responses.length > 0) {
            int randomIndex = new Random().nextInt(responses.length);
            return responses[randomIndex];
        }
        return "I'm here to help with your queries about PGC!";
    }

    // Adapter for RecyclerView with different view types for user and bot messages
    public static class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private static final int VIEW_TYPE_USER = 1;
        private static final int VIEW_TYPE_BOT = 2;

        private ArrayList<ChatMessage> chatMessages;

        public ChatAdapter(ArrayList<ChatMessage> chatMessages) {
            this.chatMessages = chatMessages;
        }

        @Override
        public int getItemViewType(int position) {
            ChatMessage message = chatMessages.get(position);
            return message.isUser() ? VIEW_TYPE_USER : VIEW_TYPE_BOT;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == VIEW_TYPE_USER) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_chat_user, parent, false);
                return new UserMessageViewHolder(view);
            } else {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_chat_bot, parent, false);
                return new BotMessageViewHolder(view);
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ChatMessage message = chatMessages.get(position);

            if (holder.getItemViewType() == VIEW_TYPE_USER) {
                ((UserMessageViewHolder) holder).bind(message);
            } else {
                ((BotMessageViewHolder) holder).bind(message);
            }
        }

        @Override
        public int getItemCount() {
            return chatMessages.size();
        }

        // ViewHolder for User Messages
        static class UserMessageViewHolder extends RecyclerView.ViewHolder {
            TextView messageText;

            UserMessageViewHolder(View itemView) {
                super(itemView);
                messageText = itemView.findViewById(R.id.text_message_user);
            }

            void bind(ChatMessage message) {
                messageText.setText(message.getMessage());
            }
        }

        // ViewHolder for Bot Messages
        static class BotMessageViewHolder extends RecyclerView.ViewHolder {
            TextView messageText;

            BotMessageViewHolder(View itemView) {
                super(itemView);
                messageText = itemView.findViewById(R.id.text_message_bot);
            }

            void bind(ChatMessage message) {
                messageText.setText(message.getMessage());
            }
        }
    }

    // ChatMessage class (unchanged)
    public static class ChatMessage {
        private String message;
        private boolean isUser;

        public ChatMessage(String message, boolean isUser) {
            this.message = message;
            this.isUser = isUser;
        }

        public String getMessage() {
            return message;
        }

        public boolean isUser() {
            return isUser;
        }
    }
}
